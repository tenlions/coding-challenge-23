package de.nloewes.roshambr.exception;

import de.nloewes.roshambr.model.dto.Exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Provides custom exception handlers for exceptions that may be thrown in the context of this application and their conversion
 * to rest-compliant representations
 */
@ControllerAdvice
public class ExceptionHandler {

    /**
     * Custom exception handler for any thrown {@link InvalidChoiceException}.
     * Handles conversion to a REST-Compliant {@link Exception}
     *
     * @param exception the thrown InvalidChoiceException
     * @return a ResponseEntity with an according HTTP status and the converted exception as body
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Exception> handleInvalidChoiceException(InvalidChoiceException exception) {
        Exception restException = new Exception();
        restException.setHttpStatus(exception.getStatus().value());
        restException.setCode(exception.getError().getCode());
        restException.setMessage(exception.getError().getMessage());
        restException.setValue(exception.getValue());

        return new ResponseEntity<>(restException, exception.getStatus());
    }
}
