package com.example.rsoi_course_work.session_service;

import com.example.rsoi_course_work.session_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select c from User c where c.user_uid = ?1")
    Optional<User> findByUser_uid(UUID user_uid);

    @Query("select c from User c where c.login = ?1")
    Optional<User> findByLogin(String login);
}