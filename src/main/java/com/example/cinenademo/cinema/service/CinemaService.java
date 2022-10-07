package com.example.cinenademo.cinema.service;

import com.example.cinenademo.cinema.model.Cinema;
import com.example.cinenademo.cinema.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    @Autowired
    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public Cinema findById (Long id) {
        return cinemaRepository.getOne(id);
    }

    public List<Cinema> findAll() {
        return cinemaRepository.findAll();
    }

    public Cinema save(Cinema cinema) {
        return cinemaRepository.save(cinema);
    }

    public void deleteById(Long id) {
        cinemaRepository.deleteById(id);
    }
}
