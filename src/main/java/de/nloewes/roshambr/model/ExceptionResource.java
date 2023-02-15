package de.nloewes.roshambr.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO class for REST API representation of any Exceptions encountered during runtime
 *
 * @author nloewes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResource {

    private int httpStatus;

    private int code;

    private String message;

    private String value;

    /**
     * @return the HTTP status associated with the exception
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * @return the error code associated with the exception
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the message associated with the exception
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the optional value that caused the exception
     */
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
