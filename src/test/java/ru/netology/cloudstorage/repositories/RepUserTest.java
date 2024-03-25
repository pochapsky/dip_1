package ru.netology.cloudstorage.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;
import ru.netology.cloudstorage.models.User;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;




@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepUserTest {

    public static final String MY_LOGIN = "My_login";
    public static final String MY_PASS = "My_pass";
    public static final String AUTH_USERNAME_3 = "Auth_Username3";
    //------------------------------------------------------------

    @Autowired
    private UserRepository userRep;

    @BeforeEach
    void setUp() {
        User user = new User(RandomUtils.nextLong(), MY_LOGIN, MY_PASS, null);
        userRep.save(user);
    }

    @Test
    void findUserByLog() {
        assertEquals(MY_LOGIN, userRep.findUserByLogin(MY_LOGIN).getLogin());
    }

    @Test
    void notFindNotUserByLogin() {
        assertNull(userRep.findUserByLogin(AUTH_USERNAME_3));
    }
}
