package pet.tasktrackerapi.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pet.tasktrackerapi.api.model.Role;
import pet.tasktrackerapi.api.model.User;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByUsername() {

        String username = "test";
        User expected = User.builder()
                .username(username)
                .password("test")
                .role(Role.USER)
                .build();

        underTest.save(expected);

        User actual = underTest.findByUsername(username).orElseThrow();

        assertEquals(expected, actual);
    }


    @Test
    void checkIfExistsUserByUsername() {

        String username = "test";
        User user = User.builder()
                .username(username)
                .password("test")
                .role(Role.USER)
                .build();
        underTest.save(user);

        boolean actual = underTest.existsUserByUsername(username);

        assertTrue(actual);
    }

    @Test
    void checkIfNotExistsUserByUsername() {

        String username = "notExist";
        boolean actual = underTest.existsUserByUsername(username);

        assertFalse(actual);
    }

}
