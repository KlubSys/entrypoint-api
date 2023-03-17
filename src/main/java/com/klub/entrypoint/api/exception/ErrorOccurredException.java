package com.klub.entrypoint.api.exception;

public class ErrorOccurredException extends Exception {

    public ErrorOccurredException() {
        super();
    }

    public ErrorOccurredException(String message) {
        super(message);
    }
}
