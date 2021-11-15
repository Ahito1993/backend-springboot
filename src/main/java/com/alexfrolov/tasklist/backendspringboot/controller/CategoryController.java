package com.alexfrolov.tasklist.backendspringboot.controller;

import com.alexfrolov.tasklist.backendspringboot.entity.Category;
import com.alexfrolov.tasklist.backendspringboot.entity.Priority;
import com.alexfrolov.tasklist.backendspringboot.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory (@RequestBody Category category) {

        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity("redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory (@RequestBody Category category) {
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Category> getById (@PathVariable Long id) {
        return ResponseEntity.ok(categoryRepository.findById(id).get());
    }

}
