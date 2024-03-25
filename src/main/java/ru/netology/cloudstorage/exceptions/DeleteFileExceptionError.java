package ru.netology.cloudstorage.exceptions;

public class DeleteFileExceptionError extends RuntimeException {
    public DeleteFileExceptionError() {
        super("Error delete file");
    }
}
