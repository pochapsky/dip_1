package ru.netology.cloudstorage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.cloudstorage.exceptions.*;
import ru.netology.cloudstorage.response.ResponseError;


@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BadCredentialsExceptionError.class)
    public ResponseEntity<?> handlerBadCredentials() {
        ResponseError errorResponse = new ResponseError("Error Bad Credentials", 0);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedExceptionError.class)
    public ResponseEntity<?> handlerErrorUnauthorized() {
        ResponseError errorResponse = new ResponseError("Error Unauthorized", 0);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InputDataExceptionError.class)
    public ResponseEntity<?> handlerErrorInputData() {
        ResponseError errorResponse = new ResponseError("Error Input Data", 0);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeleteFileExceptionError.class)
    public ResponseEntity<?> handlerDeleteFile() {
        ResponseError errorResponse = new ResponseError("Error Delete File", 0);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UploadFileExceptionError.class)
    public ResponseEntity<?> handlerUploadFile() {
        ResponseError errorResponse = new ResponseError("Error Upload File", 0);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}