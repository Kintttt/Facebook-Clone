package com.facebook.minifacebook.repository;

import com.facebook.minifacebook.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class UserRepositoryTest {

    @BeforeEach
    void setUp() {
    }

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void createUser(){
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("Tems");
        user.setLastName("Kint");
        user.setEmail("akins@gmail.com");
        user.setPassword("12345");

        User savedUser = repo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getUserId());
        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());

    }
}