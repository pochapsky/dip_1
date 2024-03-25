package ru.netology.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.cloudstorage.request.RequestAuth;

import jakarta.validation.Valid;
import ru.netology.cloudstorage.response.JwtTokenResponse;
import ru.netology.cloudstorage.services.AuthenticationService;


@RestController
public class AuthenticationControllerDiplom {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationControllerDiplom(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody RequestAuth requestAuth) {
        JwtTokenResponse token = authenticationService.login(requestAuth);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String authToken) {
        authenticationService.logout(authToken);
        return new ResponseEntity<>("Success logout", HttpStatus.OK);
    }
}