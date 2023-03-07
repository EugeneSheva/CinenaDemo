package com.example.cinenademo.cinema.repository.pages;

import com.example.cinenademo.cinema.model.pages.Page;
import com.example.cinenademo.cinema.model.pages.Pageuser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageuserRepository extends JpaRepository<Pageuser, Long> {
}
