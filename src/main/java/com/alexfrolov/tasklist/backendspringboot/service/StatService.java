package com.alexfrolov.tasklist.backendspringboot.service;

import com.alexfrolov.tasklist.backendspringboot.entity.Stat;
import com.alexfrolov.tasklist.backendspringboot.repository.StatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
// транзакция завершится, если все методы выполнятся без ошибок
// в противном случае все операции откатятся
// transaction will be ended, if all methods are executed without errors
// otherwise, all operations will be rolled back
@Transactional
public class StatService {

    private final StatRepository repository;

    public StatService(StatRepository repository) {
        this.repository = repository;
    }

    public Stat findById (Long id) {
        return repository.findById(id).get();
    }
}
