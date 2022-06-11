package com.licenta.shmafaerserver.exception.CustomExceptions;

public class InvalidPasswordResetToken extends AbstractCustomException{
    public InvalidPasswordResetToken(String message) {
        super(message);
    }
}
