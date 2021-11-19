package com.alexfrolov.tasklist.backendspringboot.controller;

import com.alexfrolov.tasklist.backendspringboot.entity.Priority;
import com.alexfrolov.tasklist.backendspringboot.repository.PriorityRepository;
import com.alexfrolov.tasklist.backendspringboot.search.PrioritySearchValues;
import com.alexfrolov.tasklist.backendspringboot.service.PriorityService;
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
@RequestMapping("/priority")
public class PriorityController {

    // доступ к данным из БД
    // access to data from the database
    private final PriorityService priorityService;

    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    // automatic implementation of a class instance via the constructor
    // we don't use @Autowired for a class variable, because "Field injection is not recommended "
    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Priority>> findAll() {

        showMethodName("PriorityController: findAll() ------------------------------------------------------");
        return ResponseEntity.ok(priorityService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> addPriority(@RequestBody Priority priority) {

        showMethodName("PriorityController: addPriority() ---------------------------------------------------");
        // проверка на обязательные параметры
        // checking for required parameters
        if (priority.getId() != null && priority.getId() != 0) {
            return new ResponseEntity("redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        // if title value is empty
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение color
        // if color value is empty
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        // save works for both adding and updating
        return ResponseEntity.ok(priorityService.addPriority(priority));
    }

    @PutMapping("/update")
    public ResponseEntity<Priority> updatePriority(@RequestBody Priority priority) {

        showMethodName("PriorityController: updatePriority() ---------------------------------------------------");
        // проверка на обязательные параметры
        // checking for required parameters
        if (priority.getId() == null || priority.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        // if title value is empty
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение color
        // if color value is empty
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        // save works for both adding and updating
        priorityService.updatePriority(priority);

        return new ResponseEntity(HttpStatus.OK);
    }

    // параметр id передается не в BODY запроса, а в самом URL
    // the id parameter is passed in URL, not in request BODY
    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> getById(@PathVariable Long id) {

        showMethodName("PriorityController: getById() ---------------------------------------------------");
        Priority priority;

        // try-catch используется для отображения своего текста, а не stacktrace
        // try-catch is used for displaying its own text, not stacktrace
        try {
            priority = priorityService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id " + id + " not found", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(priority);
    }

    // параметр id передается не в BODY запроса, а в самом URL
    // the id parameter is passed in URL, not in request BODY
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Priority> deleteById(@PathVariable Long id) {

        showMethodName("PriorityController: deleteById() ---------------------------------------------------");
        // try-catch используется для отображения своего текста, а не stacktrace
        // try-catch is used for displaying its own text, not stacktrace
        try {
            priorityService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id " + id + " not found", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    // поиск по любым параметрам PrioritySearchValues
    // search by any PrioritySearchValues parameters
    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues) {

        showMethodName("PriorityController: search() ---------------------------------------------------");
        // если вместо текста будет пусто или null - вернутся все приоритеты
        // if the text is empty or null, all priorities will be returned
        return ResponseEntity.ok(priorityService.findByTitle(prioritySearchValues.getText()));
    }
}
