package ru.netology.cloudstorage.exceptions;

public class BadCredentialsExceptionError extends RuntimeException {

    public BadCredentialsExceptionError() {
        super("Error bad credentials");
    }
}
