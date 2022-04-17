package com.licenta.shmafaerserver.exception.CustomExceptions;

public class InvalidRegisterRole extends AbstractCustomException {
    public InvalidRegisterRole() {
        super("Invalid role for register (only 'STUDENT' and 'USER' allowed)");
    }
}
