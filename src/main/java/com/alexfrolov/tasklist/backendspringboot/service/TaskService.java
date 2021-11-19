package com.alexfrolov.tasklist.backendspringboot.service;

import com.alexfrolov.tasklist.backendspringboot.entity.Task;
import com.alexfrolov.tasklist.backendspringboot.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// транзакция завершится, если все методы выполнятся без ошибок
// в противном случае все операции откатятся
// transaction will be ended, if all methods are executed without errors
// otherwise, all operations will be rolled back
@Transactional
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll () {
        return repository.findAll();
    }

    public Task addTask(Task task) {
        return repository.save(task);
    }

    public Task updateTask(Task task) {
        return repository.save(task);
    }

    public void deleteById (Long id) {
        repository.deleteById(id);
    }

    public Page findByParams(String title, Integer completed, Long priorityId, Long categoryId, Pageable pageable) {
        return repository.findByParams(title, completed, priorityId, categoryId, pageable);
    }

    public Task findById(Long id) {
        return repository.findById(id).get();
    }

}
