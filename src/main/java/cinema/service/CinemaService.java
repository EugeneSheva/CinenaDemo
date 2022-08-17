package cinema.service;

import cinema.model.Cinema;
import cinema.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CinemaService {
    private final CinemaRepository cinemaRepository;
    @Autowired
    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public Cinema findById (Long id) {
        return cinemaRepository.getOne(id);
    }

    public List<Cinema> findAll() {
        return cinemaRepository.findAll();
    }

    public Cinema saveUser(Cinema cinema) {
        return cinemaRepository.save(cinema);
    }

    public void deleteById(Long id) {
        cinemaRepository.deleteById(id);
    }
}
