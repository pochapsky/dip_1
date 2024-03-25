package ru.netology.cloudstorage.exceptions;

public class UploadFileExceptionError extends RuntimeException {
    public UploadFileExceptionError() {
        super("Error upload file");
    }
}
