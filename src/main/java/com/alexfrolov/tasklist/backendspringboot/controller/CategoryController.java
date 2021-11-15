package com.alexfrolov.tasklist.backendspringboot.controller;

import com.alexfrolov.tasklist.backendspringboot.entity.Category;
import com.alexfrolov.tasklist.backendspringboot.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/test")
    public List<Category> getAll () {
        return categoryRepository.findAll();
    }

    @PostMapping ("/add")
    public void addCategory (@RequestBody Category category) {
        categoryRepository.save(category);
    }

}
