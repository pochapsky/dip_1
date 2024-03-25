package ru.netology.cloudstorage.response;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class ResponseFile {
    private String filename;
    private long size;
}