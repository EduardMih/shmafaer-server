package com.licenta.shmafaerserver.exception.CustomExceptions;

public class ProjectLinkAlreadyExists extends AbstractCustomException{
    public ProjectLinkAlreadyExists() {
        super("Another project with same repository link already exists");
    }
}
