package com.example.cinenademo.cinema.repository;

import com.example.cinenademo.cinema.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, Long> {
}
