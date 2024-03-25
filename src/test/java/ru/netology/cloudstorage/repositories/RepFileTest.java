package ru.netology.cloudstorage.repositories;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.netology.cloudstorage.models.File;
import ru.netology.cloudstorage.models.User;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepFileTest {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;
    private User userRepSave;
    private File fileRepSave;

    @BeforeEach
    void setUp() {
        User user = new User(RandomUtils.nextLong(), "login1", "password1", null);
        userRepSave = userRepository.save(user);

        File file = new File(RandomUtils.nextLong(), "file_name", LocalDateTime.now(),
                RandomUtils.nextLong(), "".getBytes(), userRepSave);
        fileRepSave = fileRepository.save(file);
    }

    @Test
    void findAllByUser() {
        assertEquals(List.of(fileRepSave), fileRepository.findAllByUser(userRepSave));
    }

    @Test
    void findByUserAndFilename() {
        assertEquals(fileRepSave, fileRepository.findByUserAndFilename(userRepSave, fileRepSave.getFilename()));
    }

    @Test
    void deleteUserAndFilename() {
        File beforeDel = fileRepository.findByUserAndFilename(userRepSave, fileRepSave.getFilename());
        assertNotNull(beforeDel);
        int delRows = fileRepository.deleteByUserAndFilename(userRepSave, fileRepSave.getFilename());
        Assert.assertEquals(delRows, 1);
    }
}
