package com.licenta.shmafaerserver.exception;

import com.licenta.shmafaerserver.exception.CustomExceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();

        ex.getBindingResult().getAllErrors().forEach((error) ->
        {
            String fieldName = ((FieldError) error).getField();
            String errMes = error.getDefaultMessage();
            response.getErrors().put(fieldName, errMes);
        });

        response.setDateTime(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(InvalidRegisterRole.class)
    public ResponseEntity<Object> handleInvalidRegisterRole(InvalidRegisterRole ex)
    {

        return new ResponseEntity<>(customExceptionToResponse(ex, "Register"), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<Object> handleUserAlreadyExists(UserAlreadyExists ex)
    {

        return new ResponseEntity<>(customExceptionToResponse(ex, "Register"), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(InvalidStudentID.class)
    public ResponseEntity<Object> handleInvalidStudentID(InvalidStudentID ex)
    {

        return new ResponseEntity<>(customExceptionToResponse(ex, "Register"), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({InvalidProjectStructure.class,
            UnknownUserEmail.class,
            UnknownProjectType.class,
            ProjectLinkAlreadyExists.class,
            InvalidUserRole.class,
            SoftwareHeritageCommunicationException.class,
            UnknownProjectRepoLink.class,
            InvalidOldPassword.class,
            InvalidConfirmationToken.class,
            InvalidPasswordResetToken.class,
            InvalidRefreshToken.class
    })
    public ResponseEntity<Object> handleCustomExceptions(AbstractCustomException ex)
    {

        return new ResponseEntity<>(customExceptionToResponse(ex, "errorMessage"), HttpStatus.BAD_REQUEST);

    }

    private ExceptionResponse customExceptionToResponse(AbstractCustomException ex, String errorKey)
    {
        ExceptionResponse response = new ExceptionResponse();

        response.getErrors().put(errorKey, ex.getMessage());

        response.setDateTime(LocalDateTime.now());

        return response;

    }
}
