package com.example.cinenademo.cinema.repository;

import com.example.cinenademo.cinema.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {
}
