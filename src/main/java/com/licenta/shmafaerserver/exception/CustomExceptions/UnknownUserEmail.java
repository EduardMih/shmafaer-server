package com.licenta.shmafaerserver.exception.CustomExceptions;

public class UnknownUserEmail extends AbstractCustomException {
    public UnknownUserEmail() {
        super("Unknown user with specified email");
    }
}
