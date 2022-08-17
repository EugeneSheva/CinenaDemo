package cinema.service;

import cinema.model.Hall;
import cinema.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HallService {
    private final HallRepository hallRepository;
    @Autowired
    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public Hall findById (Long id) {
        return hallRepository.getOne(id);
    }

    public List<Hall> findAll() {
        return hallRepository.findAll();
    }

    public Hall saveUser(Hall hall) {
        return hallRepository.save(hall);
    }

    public void deleteById(Long id) {
        hallRepository.deleteById(id);
    }
}
