package com.devstudios.store.devstudios_store_server.infrastructure.services;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.devstudios.store.devstudios_store_server.config.EnvsConfig.getKeyJwt;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




@Service
public class JwtValidationFilter extends OncePerRequestFilter {


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
            Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) extractClaim(token).get("authorities");

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
            .verifyWith(getKeyJwt())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

}