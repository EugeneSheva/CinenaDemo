package com.example.cinenademo.cinema.service;

import com.example.cinenademo.cinema.model.Pagecontacts;
import com.example.cinenademo.cinema.repository.PagecontactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagecontactsService {
    private final PagecontactsRepository pagecontactsRepository;
    @Autowired
    public PagecontactsService(PagecontactsRepository pagecontactsRepository) {
        this.pagecontactsRepository = pagecontactsRepository;
    }

    public Pagecontacts findById (Long id) {
        return pagecontactsRepository.getOne(id);
    }

    public List<Pagecontacts> findAll() {
        return pagecontactsRepository.findAll();
    }

    public Pagecontacts saveUser(Pagecontacts pagecontacts) {
        return pagecontactsRepository.save(pagecontacts);
    }

    public void deleteById(Long id) {
        pagecontactsRepository.deleteById(id);
    }
}
