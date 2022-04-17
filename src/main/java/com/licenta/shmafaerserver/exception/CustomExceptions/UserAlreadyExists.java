package com.licenta.shmafaerserver.exception.CustomExceptions;

public class UserAlreadyExists extends AbstractCustomException{
    public UserAlreadyExists() {
        super("A user with same email already exists!");
    }

}
