package com.mwaltman.devops.core.exceptions;

public class SystemPropertyException extends RuntimeException {

    public SystemPropertyException(String message) {
        super(message);
    }

    public SystemPropertyException(String message, Throwable cause) {
        super(message, cause);
    }
}
