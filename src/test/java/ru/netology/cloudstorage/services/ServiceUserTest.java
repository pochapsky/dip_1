package ru.netology.cloudstorage.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.netology.cloudstorage.models.User;
import ru.netology.cloudstorage.repositories.UserRepository;
import ru.netology.cloudstorage.exceptions.UnauthorizedExceptionError;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ServiceUserTest {

    public static final Long AUTH_USER_ID_3 = 103L;
    public static final String AUTH_USERNAME_3 = "Auth_Username3";
    public static final String AUTH_PASSWORD_3 = "Auth_Password3";
    public static final User USER_3 = new User(AUTH_USER_ID_3, AUTH_USERNAME_3, AUTH_PASSWORD_3, null);
    //-------------------------------------------------------------------------------------------------------
    public static final String AUTH_USERNAME_4 = "Auth_Username4";
    //-------------------------------------------------------------------------------------------------------

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRep;

    @BeforeEach
    void setUp() {
        Mockito.when(userRep.findUserByLogin(AUTH_USERNAME_3)).thenReturn(USER_3);
    }

    @Test
    void loadUserByNameNotAuthorizedException() {
        Assertions.assertThrows(UnauthorizedExceptionError.class,
                () -> userService.loadUserByUsername(AUTH_USERNAME_4));
    }

    @Test
    void loadUserByName() {
        Assertions.assertEquals(USER_3, userService.loadUserByUsername(AUTH_USERNAME_3));
    }
}
