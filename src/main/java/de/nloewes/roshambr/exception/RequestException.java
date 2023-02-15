package de.nloewes.roshambr.exception;

import org.springframework.http.HttpStatus;

/**
 * Base runtime exception class to be thrown in request contexts (e.g. 'Bad Request').
 * Exceptions shall include a specific causing value, if available.
 *
 * @author nloewes
 */
public class RequestException extends ResponseException {

    private final String value;

    public RequestException(HttpStatus status, Errors error, String value) {
        super(status, error);
        this.value = value;
    }

    /**
     *
     * @return the specific value that caused the exception
     */
    public String getValue() {
        return value;
    }
}
