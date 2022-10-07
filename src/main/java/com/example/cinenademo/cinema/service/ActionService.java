package com.example.cinenademo.cinema.service;

import com.example.cinenademo.cinema.model.Action;
import com.example.cinenademo.cinema.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ActionService {
    private final ActionRepository actionRepository;
    @Autowired
    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public Action findById (Long id) {
        return actionRepository.getOne(id);
    }

    public List<Action> findAll() {
        return actionRepository.findAll();
    }

    public Action save(Action action) {
        return actionRepository.save(action);
    }

    public void deleteById(Long id) {
        actionRepository.deleteById(id);
    }
}
