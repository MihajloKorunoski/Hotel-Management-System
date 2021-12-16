package com.dians.hotelmanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.dians.hotelmanagement.model.User;
import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import javax.annotation.PostConstruct;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("manov@gmail.com");
        user.setPassword("manov2020");
        user.setFirstName("Aleksandar");
        user.setLastName("Manov");

        User savedUser = repo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getEmail());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

    }
}