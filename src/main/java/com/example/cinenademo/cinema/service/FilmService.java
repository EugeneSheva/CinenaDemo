package com.example.cinenademo.cinema.service;

import com.example.cinenademo.cinema.model.Film;
import com.example.cinenademo.cinema.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FilmService {
    private final FilmRepository filmRepository;
    @Autowired

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Film findById (Long id) {
        return filmRepository.getOne(id);
    }

    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    public void deleteById(Long id) {
        filmRepository.deleteById(id);
    }
}
