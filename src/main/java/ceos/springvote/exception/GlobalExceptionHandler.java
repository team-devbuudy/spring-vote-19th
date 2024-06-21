package ceos.springvote.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseTemplate> handleCustomException(ResponseException exception){
        log.error("Exception description: " + exception.getMessage());
        return ResponseTemplate.toResponseEntity(exception.getStatus(), exception.getMessage());
    }
}
