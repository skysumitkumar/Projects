package com.sumit.quizApp.repository.user;

import com.sumit.quizApp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public interface UserDao extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
