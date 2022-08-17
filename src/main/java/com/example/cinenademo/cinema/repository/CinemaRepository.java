package com.example.cinenademo.cinema.repository;

import com.example.cinenademo.cinema.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema,Long> {
}
