package com.example.cinenademo.cinema.repository;

import com.example.cinenademo.cinema.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
}
