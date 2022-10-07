package com.example.cinenademo.cinema.service;

import com.example.cinenademo.cinema.model.Banneraction;


import com.example.cinenademo.cinema.repository.BanneractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BanneractionService {
    private final BanneractionRepository banneractionRepository;
    @Autowired
    public BanneractionService(BanneractionRepository banneractionRepository) {
        this.banneractionRepository = banneractionRepository;
    }

    public Banneraction findById (Long id) {
        return banneractionRepository.getOne(id);
    }

    public List<Banneraction> findAll() {
        return banneractionRepository.findAll();
    }

    public Banneraction save(Banneraction banneraction) {
        return banneractionRepository.save(banneraction);
    }

    public void deleteById(Long id) {
        banneractionRepository.deleteById(id);
    }
}
