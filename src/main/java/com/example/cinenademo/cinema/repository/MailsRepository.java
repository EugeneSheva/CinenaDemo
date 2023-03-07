package com.example.cinenademo.cinema.repository;

import com.example.cinenademo.cinema.model.Mails;
import com.example.cinenademo.cinema.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailsRepository extends JpaRepository<Mails, Long> {
}
