package com.devstudios.store.devstudios_store_server.infrastructure.services;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.devstudios.store.devstudios_store_server.config.EnvsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




@Service
public class JwtValidationFilter extends OncePerRequestFilter {

    @Autowired
    EnvsConfig envsConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");

        if( header == null || !header.startsWith("Bearer ") || SecurityContextHolder.getContext().getAuthentication() != null ){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(7);
            String email = extractClaim(token).getSubject();

            Collection<? extends GrantedAuthority> authorities = ((Collection<Map<String, String>>) extractClaim(token).get("authorities"))
            .stream()
            .map(auth -> new SimpleGrantedAuthority(auth.get("authority")))
            .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Map<String, Object> res = new HashMap<>();

            res.put("status", 401);
            res.put("err", "session expired");
            res.put("date", new Date());
            res.put("message", "error");

            response.getWriter()
                .write(new ObjectMapper().writeValueAsString(res)
            );
            response.setStatus(401);
            response.setContentType("application/json");
        }
    }


    private Claims extractClaim( String token ){
        return Jwts.parser()
            .verifyWith(envsConfig.getKeyJwt())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

}
