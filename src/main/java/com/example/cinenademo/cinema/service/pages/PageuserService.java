package com.example.cinenademo.cinema.service.pages;

import com.example.cinenademo.cinema.model.pages.Page;
import com.example.cinenademo.cinema.model.pages.Pageuser;
import com.example.cinenademo.cinema.repository.pages.PageRepository;
import com.example.cinenademo.cinema.repository.pages.PageuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageuserService {
    private final PageuserRepository pageuserRepository;

    @Autowired
    public PageuserService(PageuserRepository pageuserRepository) {
        this.pageuserRepository = pageuserRepository;
    }

    public Pageuser findById(Long id) {
        return pageuserRepository.getOne(id);
    }

    public List<Pageuser> findAll() {
        return pageuserRepository.findAll();
    }

    public Pageuser save(Pageuser pageuser) {
        return pageuserRepository.save(pageuser);
    }

    public void deleteById(Long id) {
        pageuserRepository.deleteById(id);
    }
}
