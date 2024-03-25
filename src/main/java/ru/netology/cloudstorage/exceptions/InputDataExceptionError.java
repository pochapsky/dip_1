package ru.netology.cloudstorage.exceptions;

public class InputDataExceptionError extends RuntimeException {
    public InputDataExceptionError() {
        super("Error input data");
    }
}
