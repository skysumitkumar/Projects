package com.sumit.quizApp.repository.admin;

import com.sumit.quizApp.model.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUsername(String username);


}
