package com.example.cinenademo.cinema.repository;

import com.example.cinenademo.cinema.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
