package com.example.cinenademo.cinema.service;

import com.example.cinenademo.cinema.model.Hall;
import com.example.cinenademo.cinema.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HallService {
    private final HallRepository hallRepository;
    @Autowired
    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public Hall findById (Long id) {
        return hallRepository.getOne(id);
    }

    public List<Hall> findAll() {
        return hallRepository.findAll();
    }

    public Hall save(Hall hall) {
        return hallRepository.save(hall);
    }

    public void deleteById(Long id) {
        hallRepository.deleteById(id);
    }
}
