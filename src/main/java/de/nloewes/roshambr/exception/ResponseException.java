package de.nloewes.roshambr.exception;

import org.springframework.http.HttpStatus;

/**
 * Base runtime exception class to be thrown for all causes that need to be mapped to a specific response.
 * Exceptions shall be mapped to a specific HTTP status and error code.
 *
 * @author nloewes
 */
public class ResponseException extends RuntimeException {

    private final HttpStatus status;

    private final Errors error;

    public ResponseException(HttpStatus status, Errors error) {
        this.status = status;
        this.error = error;
    }

    /**
     *
     * @return the HTTP status associated with the exception
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     *
     * @return the specific error that caused the exception
     */
    public Errors getError() {
        return error;
    }
}
