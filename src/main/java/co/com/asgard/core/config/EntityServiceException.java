package co.com.asgard.core.config;

import org.springframework.http.HttpStatus;

public class EntityServiceException extends RuntimeException {
    private final HttpStatus httpStatus;

    public EntityServiceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}