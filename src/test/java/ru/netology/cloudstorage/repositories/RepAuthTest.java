package ru.netology.cloudstorage.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.cloudstorage.models.User;
import org.junit.jupiter.api.Assertions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RepAuthTest {
    public static final String AUTH_TOKEN_1 = "Auth_Token1";
    public static final Long AUTH_USER_ID_1 = 101L;
    public static final String AUTH_USERNAME_1 = "Auth_Username1";
    public static final String AUTH_PASSWORD_1 = "Auth_Password1";
    public static final User USER_1 = new User(AUTH_USER_ID_1, AUTH_USERNAME_1, AUTH_PASSWORD_1, null);

    //-------------------------------------------------------------------------------------------------------
    public static final String AUTH_TOKEN_2 = "Auth_Token2";
    public static final Long AUTH_USER_ID_2 = 102L;
    public static final String AUTH_USERNAME_2 = "Auth_Username2";
    public static final String AUTH_PASSWORD_2 = "Auth_Password2";
    public static final User USER_2 = new User(AUTH_USER_ID_2, AUTH_USERNAME_2, AUTH_PASSWORD_2, null);
    //-------------------------------------------------------------------------------------------------------

    private AuthRepository authRep;
    private final Map<String, User> tokensUser = new ConcurrentHashMap<>();

    @BeforeEach
    void authUp() {
        authRep = new AuthRepository();
        authRep.saveAuthenticationUser(AUTH_TOKEN_1, USER_1);
        tokensUser.clear();
        tokensUser.put(AUTH_TOKEN_1, USER_1);
    }

    @Test
    void getUserByAuthToken() {
        Assertions.assertEquals(tokensUser.get(AUTH_TOKEN_1), authRep.getAuthenticationUserByToken(AUTH_TOKEN_1));
    }

    @Test
    void deleteAuthUserByToken() {
        User userBeforeDel = authRep.getAuthenticationUserByToken(AUTH_TOKEN_1);
        Assertions.assertNotNull(userBeforeDel);
        authRep.deleteAuthenticationUserByToken(AUTH_TOKEN_1);
        User userAfterDel = authRep.getAuthenticationUserByToken(AUTH_TOKEN_1);
        Assertions.assertNull(userAfterDel);
    }

    @Test
    void setAuthTokenAndUser() {
        User userBeforeSet = authRep.getAuthenticationUserByToken(AUTH_TOKEN_2);
        Assertions.assertNull(userBeforeSet);
        authRep.saveAuthenticationUser(AUTH_TOKEN_2, USER_2);
        User userAfterSet = authRep.getAuthenticationUserByToken(AUTH_TOKEN_2);
        Assertions.assertEquals(USER_2, userAfterSet);
    }
}
