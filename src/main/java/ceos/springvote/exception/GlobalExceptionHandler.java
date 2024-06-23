package ceos.springvote.exception;

import ceos.springvote.dto.MemberErrorCodeResponse;
import ceos.springvote.exception.error.MemberErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseTemplate> handleCustomException(ResponseException exception){
        log.error("Exception description: " + exception.getMessage());
        return ResponseTemplate.toResponseEntity(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseTemplate> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Collecting field-specific error messages
        StringBuilder messageBuilder = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            // Append message for each field
            messageBuilder.append("'").append(fieldName).append("' 필드가 누락되었습니다: ").append(errorMessage).append(". ");
        });

        // Removing the trailing space and period
        String unifiedErrorMessage = messageBuilder.toString().trim();
        if (unifiedErrorMessage.endsWith(".")) {
            unifiedErrorMessage = unifiedErrorMessage.substring(0, unifiedErrorMessage.length() - 1);
        }
        // Returning custom error response
        return ResponseTemplate.toResponseEntity(HttpStatus.BAD_REQUEST, unifiedErrorMessage);
    }
}
