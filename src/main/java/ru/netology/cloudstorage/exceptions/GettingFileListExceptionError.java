package ru.netology.cloudstorage.exceptions;

public class GettingFileListExceptionError extends RuntimeException {
    public GettingFileListExceptionError() {
        super("Error getting file list");
    }
}
