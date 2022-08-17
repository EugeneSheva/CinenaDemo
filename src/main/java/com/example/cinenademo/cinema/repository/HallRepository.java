package com.example.cinenademo.cinema.repository;

import com.example.cinenademo.cinema.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository extends JpaRepository<Hall, Long> {
}
