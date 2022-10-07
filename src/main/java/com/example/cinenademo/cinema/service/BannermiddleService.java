package com.example.cinenademo.cinema.service;

import com.example.cinenademo.cinema.model.Bannermain;
import com.example.cinenademo.cinema.model.Bannermiddle;
import com.example.cinenademo.cinema.repository.BannermainRepository;
import com.example.cinenademo.cinema.repository.BannermiddleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannermiddleService {
    private final BannermiddleRepository bannermiddleRepository;
    @Autowired
    public BannermiddleService(BannermiddleRepository bannermiddleRepository) {
        this.bannermiddleRepository = bannermiddleRepository;
    }

    public Bannermiddle findById (Long id) {
        return bannermiddleRepository.getOne(id);
    }

    public List<Bannermiddle> findAll() {
        return bannermiddleRepository.findAll();
    }

    public Bannermiddle save(Bannermiddle bannermiddle) {
        return bannermiddleRepository.save(bannermiddle);
    }

    public void deleteById(Long id) {
        bannermiddleRepository.deleteById(id);
    }
}
