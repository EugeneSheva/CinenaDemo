package com.example.cinenademo.cinema.service;

import com.example.cinenademo.cinema.model.News;
import com.example.cinenademo.cinema.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NewsService {
    private final NewsRepository newsRepository;
    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News findById (Long id) {
        return newsRepository.getOne(id);
    }

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public News save(News news) {
        return newsRepository.save(news);
    }

    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}
