package ru.netology.cloudstorage.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.netology.cloudstorage.models.User;
import ru.netology.cloudstorage.repositories.AuthRepository;
import ru.netology.cloudstorage.repositories.UserRepository;
import ru.netology.cloudstorage.request.RequestAuth;
import ru.netology.cloudstorage.response.JwtTokenResponse;
import ru.netology.cloudstorage.security.JWTUtils;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ServiceAuthTest {

    public static final String TOKEN_5 = "Auth_Token5";
    public static final Long AUTH_USER_ID_5 = 105L;
    public static final String AUTH_USERNAME_5 = "Auth_Username5";
    public static final String AUTH_PASSWORD_5 = "Auth_Password5";
    public static final User USER_5 = new User(AUTH_USER_ID_5, AUTH_USERNAME_5, AUTH_PASSWORD_5, null);
    public static final JwtTokenResponse JWT_TOKEN_RESPONSE = new JwtTokenResponse(TOKEN_5);
    public static final RequestAuth REQUEST_AUTH = new RequestAuth(AUTH_USERNAME_5, AUTH_PASSWORD_5);
    public static final UsernamePasswordAuthenticationToken USERNAME_PASS_AUTH_TOKEN
            = new UsernamePasswordAuthenticationToken(AUTH_USERNAME_5, AUTH_PASSWORD_5);
    //------------------------------------------------------------------------------------------------------
    public static final String BEARER_TOKEN = "Bearer Token";
    public static final String BEARER_TOKEN_SUB_7 = BEARER_TOKEN.substring(7);
    //-------------------------------------------------------------------------------------------------------

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Test
    void login() {

        Mockito.when(userRepository.findUserByLogin(AUTH_USERNAME_5)).thenReturn(USER_5);
        Mockito.when(jwtUtils.generateToken(USER_5)).thenReturn(TOKEN_5);
        Mockito.when(userService.loadUserByUsername(AUTH_USERNAME_5)).thenReturn(USER_5);
        Assertions.assertEquals(JWT_TOKEN_RESPONSE, authenticationService.login(REQUEST_AUTH));
        Mockito.verify(authRepository, Mockito.times(1)).saveAuthenticationUser(TOKEN_5, USER_5);
        Mockito.verify(authenticationManager, Mockito.times(1))
                .authenticate(USERNAME_PASS_AUTH_TOKEN);
    }

    @Test
    void logout() {
        Mockito.when(authRepository.getAuthenticationUserByToken(BEARER_TOKEN_SUB_7)).thenReturn(USER_5);
    }
}
