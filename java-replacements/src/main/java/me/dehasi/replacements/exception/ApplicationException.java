package me.dehasi.replacements.exception;

public class ApplicationException extends RuntimeException {
    public ApplicationException() {}

    public ApplicationException(String message) {
        super(message);
    }
}
