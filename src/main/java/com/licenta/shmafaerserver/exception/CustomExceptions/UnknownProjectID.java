package com.licenta.shmafaerserver.exception.CustomExceptions;

public class UnknownProjectID extends AbstractCustomException{
    public UnknownProjectID(String message) {
        super("Unknown project id " + message);
    }
}
