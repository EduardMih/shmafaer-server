package com.licenta.shmafaerserver.exception.CustomExceptions;

public class UnknownProjectRepoLink extends AbstractCustomException{
    public UnknownProjectRepoLink(String message) {
        super("No project with repo link (" + message + ") found!");
    }
}
