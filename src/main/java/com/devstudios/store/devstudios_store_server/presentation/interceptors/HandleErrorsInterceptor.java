package com.devstudios.store.devstudios_store_server.presentation.interceptors;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devstudios.store.devstudios_store_server.infrastructure.CustomExceptions.CustomException;




@ControllerAdvice
public class HandleErrorsInterceptor {


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException( CustomException ex ){
        return getResponse(ex.getError(), ex.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDuplicatedKeys( DataIntegrityViolationException ex ){
        String[] shortMessage = ex.getMessage().split("\\(");

        String field = shortMessage[1].split("\\)")[0];
        String value = shortMessage[2].split("\\)")[0];
        String message = field + ": " + value + " already exists";

        return getResponse(message, 400);
    }


    private ResponseEntity<?> getResponse( Object err, int status ){
        Map<String, Object> res = new HashMap<>();

        res.put("date", LocalDateTime.now());
        res.put("status", status);
        res.put("err", err);
        res.put("message", "error");

        return ResponseEntity.status(status).body(res);
    }

}
