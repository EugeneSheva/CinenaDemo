package com.example.cinenademo.cinema.service;

import com.example.cinenademo.cinema.model.Page;
import com.example.cinenademo.cinema.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PageService {
    private final PageRepository pageRepository;
    @Autowired
    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public Page findById (Long id) {
        return pageRepository.getOne(id);
    }

    public List<Page> findAll() {
        return pageRepository.findAll();
    }

    public Page saveUser(Page page) {
        return pageRepository.save(page);
    }

    public void deleteById(Long id) {
        pageRepository.deleteById(id);
    }
}
