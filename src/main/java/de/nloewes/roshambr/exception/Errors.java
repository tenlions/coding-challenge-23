package de.nloewes.roshambr.exception;

public enum Errors {

    CHOICE_INVALID(1, "Invalid GameChoice");

    private final int code;

    private final String message;

    Errors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
