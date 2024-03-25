package ru.netology.cloudstorage.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import ru.netology.cloudstorage.exceptions.BadCredentialsExceptionError;
import ru.netology.cloudstorage.models.User;
import ru.netology.cloudstorage.repositories.AuthRepository;
import ru.netology.cloudstorage.repositories.UserRepository;
import ru.netology.cloudstorage.request.RequestAuth;

import ru.netology.cloudstorage.response.JwtTokenResponse;
import ru.netology.cloudstorage.security.JWTUtils;


@Service
public class AuthenticationService {
    final static Logger logger = Logger.getLogger(AuthenticationService.class);
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository, AuthRepository authRepository,
                                 JWTUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public JwtTokenResponse login(RequestAuth requestAuth) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestAuth.getLogin(),
                    requestAuth.getPassword()));
        } catch (BadCredentialsExceptionError e) {
            logger.error("Bad credentials error");
            throw new BadCredentialsExceptionError();
        }
        User user = userRepository.findUserByLogin(requestAuth.getLogin());
        String token = jwtUtils.generateToken(user);
        authRepository.saveAuthenticationUser(token, user);
        logger.info(String.format("Login  user name: %s ", user.getUsername()));
        return new JwtTokenResponse(token);
    }

    public void logout(String authToken) {
        String jwt = authToken.substring(7);
        User user = authRepository.getAuthenticationUserByToken(authToken);
        logger.info(String.format("User  logout: %s ", user.getUsername()));
        authRepository.deleteAuthenticationUserByToken(jwt);
    }
}