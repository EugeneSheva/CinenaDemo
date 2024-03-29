package com.example.cinenademo.cinema.service.pages;

import com.example.cinenademo.cinema.model.pages.Page;
import com.example.cinenademo.cinema.repository.pages.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {
    private final PageRepository pageRepository;

    @Autowired
    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public Page findById(Long id) {
        return pageRepository.getOne(id);
    }

    public List<Page> findAll() {
        return pageRepository.findAll();
    }

    public Page save(Page page) {
        return pageRepository.save(page);
    }

    public void deleteById(Long id) {
        pageRepository.deleteById(id);
    }
}
