package com.alexfrolov.tasklist.backendspringboot.service;

import com.alexfrolov.tasklist.backendspringboot.entity.Priority;
import com.alexfrolov.tasklist.backendspringboot.repository.PriorityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// транзакция завершится, если все методы выполнятся без ошибок
// в противном случае все операции откатятся
// transaction will be ended, if all methods are executed without errors
// otherwise, all operations will be rolled back
@Transactional
public class PriorityService {

    private final PriorityRepository repository;

    public PriorityService(PriorityRepository repository) {
        this.repository = repository;
    }

    public List<Priority> findAll () {
        return repository.findAllByOrderByIdAsc();
    }

    public Priority addPriority (Priority priority) {
        return repository.save(priority);
    }

    public Priority updatePriority (Priority priority) {
        return repository.save(priority);
    }

    public void deleteById (Long id) {
        repository.deleteById(id);
    }

    public Priority findById (Long id) {
        return repository.findById(id).get();
    }

    public List<Priority> findByTitle (String text) {
        return repository.findByTitle(text);
    }

}
