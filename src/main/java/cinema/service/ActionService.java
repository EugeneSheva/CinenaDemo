package cinema.service;

import cinema.model.Action;
import cinema.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    public Action saveUser(Action action) {
        return actionRepository.save(action);
    }

    public void deleteById(Long id) {
        actionRepository.deleteById(id);
    }
}
