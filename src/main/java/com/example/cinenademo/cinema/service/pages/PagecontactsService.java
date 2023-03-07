package com.example.cinenademo.cinema.service.pages;

import com.example.cinenademo.cinema.model.pages.Pagecontacts;
import com.example.cinenademo.cinema.repository.pages.PagecontactsRepository;
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

    public Pagecontacts findById(Long id) {
        return pagecontactsRepository.getOne(id);
    }

    public List<Pagecontacts> findAll() {
        return pagecontactsRepository.findAll();
    }

    public Pagecontacts save(Pagecontacts pagecontacts) {
        return pagecontactsRepository.save(pagecontacts);
    }

    public void deleteById(Long id) {
        pagecontactsRepository.deleteById(id);
    }
}
