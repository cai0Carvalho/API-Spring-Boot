package com.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.entity.User;

public interface UserRepositoy extends JpaRepository<User, Integer>{
    Optional<User> findUserByEmail(String email);
}