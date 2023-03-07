package com.example.cinenademo.cinema.repository.pages;

import com.example.cinenademo.cinema.model.pages.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, Long> {
}
