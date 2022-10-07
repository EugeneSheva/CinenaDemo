package com.example.cinenademo.cinema.service;
import com.example.cinenademo.cinema.model.Bannermain;
import com.example.cinenademo.cinema.repository.BannermainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BannermainService {
    private final BannermainRepository bannermainRepository;
    @Autowired
    public BannermainService(BannermainRepository bannermainRepository) {
        this.bannermainRepository = bannermainRepository;
    }

    public Bannermain findById (Long id) {
        return bannermainRepository.getOne(id);
    }

    public List<Bannermain> findAll() {
        return bannermainRepository.findAll();
    }

    public Bannermain save(Bannermain bannermain) {
        return bannermainRepository.save(bannermain);
    }

    public void deleteById(Long id) {
        bannermainRepository.deleteById(id);
    }
}
