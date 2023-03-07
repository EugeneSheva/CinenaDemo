package com.example.cinenademo.cinema.service.pages;

import com.example.cinenademo.cinema.model.pages.Page;
import com.example.cinenademo.cinema.model.pages.Pagemain;
import com.example.cinenademo.cinema.repository.pages.PageRepository;
import com.example.cinenademo.cinema.repository.pages.PagemainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagemainService {
    private final PagemainRepository pagemainRepository;

    @Autowired
    public PagemainService(PagemainRepository pagemainRepository) {
        this.pagemainRepository = pagemainRepository;
    }

    public Pagemain findById(Long id) {
        return pagemainRepository.getOne(id);
    }

    public List<Pagemain> findAll() {
        return pagemainRepository.findAll();
    }

    public Pagemain save(Pagemain pagemain) {
        return pagemainRepository.save(pagemain);
    }

    public void deleteById(Long id) {
        pagemainRepository.deleteById(id);
    }
}
