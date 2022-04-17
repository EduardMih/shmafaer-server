package com.licenta.shmafaerserver.exception.CustomExceptions;

public abstract class AbstractCustomException extends Exception {
    public AbstractCustomException(String message) {
        super(message);
    }
}
