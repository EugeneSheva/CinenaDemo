package com.example.cinenademo.cinema.repository;

import com.example.cinenademo.cinema.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
