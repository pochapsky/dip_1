package ru.netology.cloudstorage.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class RequestAuth {

    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
