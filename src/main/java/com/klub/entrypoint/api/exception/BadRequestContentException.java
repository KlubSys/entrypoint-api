package com.klub.entrypoint.api.exception;

public class BadRequestContentException extends Exception {

    public BadRequestContentException() {
        super();
    }

    public BadRequestContentException(String message) {
        super(message);
    }
}
