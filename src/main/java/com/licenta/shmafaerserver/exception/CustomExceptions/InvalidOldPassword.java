package com.licenta.shmafaerserver.exception.CustomExceptions;

public class InvalidOldPassword extends AbstractCustomException{
    public InvalidOldPassword()
    {
        super("Invalid old password");
    }
}
