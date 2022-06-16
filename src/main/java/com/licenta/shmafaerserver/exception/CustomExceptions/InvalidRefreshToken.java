package com.licenta.shmafaerserver.exception.CustomExceptions;

public class InvalidRefreshToken extends AbstractCustomException{
    public InvalidRefreshToken(String message) {
        super(message);
    }
}
