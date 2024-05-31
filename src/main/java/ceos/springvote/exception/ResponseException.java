package ceos.springvote.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ResponseException extends RuntimeException{
    private final HttpStatus status;


    protected ResponseException(String message,HttpStatus status) {
        super(message);
        this.status = status;
    }

}
