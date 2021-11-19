package com.alexfrolov.tasklist.backendspringboot.service;

import com.alexfrolov.tasklist.backendspringboot.entity.Category;
import com.alexfrolov.tasklist.backendspringboot.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// транзакция завершится, если все методы выполнятся без ошибок
// в противном случае все операции откатятся
// transaction will be ended, if all methods are executed without errors
// otherwise, all operations will be rolled back
@Transactional
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category addCategory (Category category) {
        return repository.save(category);
    }

    public Category updateCategory (Category category) {
        return repository.save(category);
    }

    public void deleteById (Long id) {
        repository.deleteById(id);
    }

    public List<Category> findByTitle (String text) {
        return repository.findByTitle(text);
    }

    public Category findById (Long id) {
        return repository.findById(id).get();
    }

    public List<Category> findAllByOrderByTitleAsc() {
        return repository.findAllByOrderByTitleAsc();
    }
}
