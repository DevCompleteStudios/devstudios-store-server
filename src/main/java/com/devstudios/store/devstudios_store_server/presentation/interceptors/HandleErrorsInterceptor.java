package com.devstudios.store.devstudios_store_server.presentation.interceptors;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleArgumentNotValid( MethodArgumentNotValidException ex ){
        List<String> errors = ex.getBindingResult().getFieldErrors()
            .stream()
            .map( e -> {
                return e.getField() + ": " + e.getDefaultMessage();
            }).toList();

        return getResponse(errors, 400);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInternalServerError(Exception ex){
        System.out.println(ex.getMessage()); // logger
        return getResponse("Internal server error", 500);
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
