package ru.netology.cloudstorage.exceptions;

public class UnauthorizedExceptionError extends RuntimeException {
    public UnauthorizedExceptionError() {
        super("Error unauthorized");
    }
}
