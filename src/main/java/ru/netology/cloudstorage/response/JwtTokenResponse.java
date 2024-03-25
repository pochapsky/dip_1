package ru.netology.cloudstorage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class JwtTokenResponse {

    @JsonProperty("auth-token")
    private final String authToken;
}
