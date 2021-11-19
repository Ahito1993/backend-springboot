package com.alexfrolov.tasklist.backendspringboot.controller;

import com.alexfrolov.tasklist.backendspringboot.entity.Category;
import com.alexfrolov.tasklist.backendspringboot.repository.CategoryRepository;
import com.alexfrolov.tasklist.backendspringboot.search.CategorySearchValues;
import com.alexfrolov.tasklist.backendspringboot.service.CategoryService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static com.alexfrolov.tasklist.backendspringboot.util.MyLogger.showMethodName;

// используем @RestController вместо обычного @Controller, чтобы все ответы сразу оборачивались в JSON
// we use @RestController instead of the usual @Controller so that all responses are immediately wrapped in JSON
@RestController
@RequestMapping("/category")
public class CategoryController {

    // доступ к данным из БД
    // access to data from the database
    private final CategoryService categoryService;

    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    // automatic implementation of a class instance via the constructor
    // we don't use @Autowired for a class variable, because "Field injection is not recommended "
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> findAll () {

        showMethodName("CategoryController: findAll() ------------------------------------------------------");
        return ResponseEntity.ok(categoryService.findAllByOrderByTitleAsc());
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory (@RequestBody Category category) {

        showMethodName("CategoryController: addCategory() ---------------------------------------------------");
        // проверка на обязательные параметры
        // checking for required parameters
        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity("redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        // if title value is empty
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        // save works for both adding and updating
        return ResponseEntity.ok(categoryService.addCategory(category));
    }

    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory (@RequestBody Category category) {

        showMethodName("CategoryController: updateCategory() -------------------------------------------------");
        // проверка на обязательные параметры
        // checking for required parameters
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        // if title value is empty
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        // save works for both adding and updating
        categoryService.updateCategory(category);

        return new ResponseEntity(HttpStatus.OK);
    }

    // параметр id передается не в BODY запроса, а в самом URL
    // the id parameter is passed in URL, not in request BODY
    @GetMapping("/id/{id}")
    public ResponseEntity<Category> getById (@PathVariable Long id) {

        showMethodName("CategoryController: getById() -----------------------------------------------------");
        Category category;

        // try-catch используется для отображения своего текста, а не stacktrace
        // try-catch is used for displaying its own text, not stacktrace
        try {
            category = categoryService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id " + id + " not found", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(category);
    }

    // параметр id передается не в BODY запроса, а в самом URL
    // the id parameter is passed in URL, not in request BODY
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Category> deleteById (@PathVariable Long id) {

        showMethodName("CategoryController: deleteById() ---------------------------------------------------");
        // try-catch используется для отображения своего текста, а не stacktrace
        // try-catch is used for displaying its own text, not stacktrace
        try {
            categoryService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id " + id + " not found", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    // поиск по любым параметрам CategorySearchValues
    // search by any CategorySearchValues parameters
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search (@RequestBody CategorySearchValues categorySearchValues) {

        showMethodName("CategoryController: search() ---------------------------------------------------");
        // если вместо текста будет пусто или null - вернутся все категории
        // if the text is empty or null, all categories will be returned
        return ResponseEntity.ok(categoryService.findByTitle(categorySearchValues.getText()));
    }


}
