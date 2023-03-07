package com.example.cinenademo.cinema.service;

import com.example.cinenademo.cinema.model.Mails;
import com.example.cinenademo.cinema.model.News;
import com.example.cinenademo.cinema.repository.MailsRepository;
import com.example.cinenademo.cinema.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailsService {
    private final MailsRepository mailsRepository;

    @Autowired
    public MailsService(MailsRepository mailsRepository) {
        this.mailsRepository = mailsRepository;
    }

    public Mails findById(Long id) {
        return mailsRepository.getOne(id);
    }

    public List<Mails> findAll() {
        return mailsRepository.findAll();
    }

    public Mails save(Mails mails) {
        return mailsRepository.save(mails);
    }

    public void deleteById(Long id) {
        mailsRepository.deleteById(id);
    }
}
