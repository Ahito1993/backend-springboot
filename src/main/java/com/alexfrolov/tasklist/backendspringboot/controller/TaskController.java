package com.alexfrolov.tasklist.backendspringboot.controller;

import com.alexfrolov.tasklist.backendspringboot.entity.Task;
import com.alexfrolov.tasklist.backendspringboot.search.TaskSearchValues;
import com.alexfrolov.tasklist.backendspringboot.service.TaskService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static com.alexfrolov.tasklist.backendspringboot.util.MyLogger.showMethodName;

// используем @RestController вместо обычного @Controller, чтобы все ответы сразу оборачивались в JSON
// we use @RestController instead of the usual @Controller so that all responses are immediately wrapped in JSON
@RestController
@RequestMapping("/task")
public class TaskController {

    // доступ к данным из БД
    // access to data from the database
    private final TaskService taskService;

    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    // automatic implementation of a class instance via the constructor
    // we don't use @Autowired for a class variable, because "Field injection is not recommended "
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Task>> findAll() {

        showMethodName("TaskController: findAll() ------------------------------------------------------");
        return ResponseEntity.ok(taskService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Task> addTask (@RequestBody Task task) {

        showMethodName("TaskController: addTask() ------------------------------------------------------");

        // проверка на обязательные параметры
        // checking for required parameters
        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity("redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        // if title value is empty
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(taskService.addTask(task));
    }

    @PutMapping("/update")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {

        showMethodName("TaskController: updateTask() ---------------------------------------------------");

        // проверка на обязательные параметры
        // checking for required parameters
        if (task.getId() == null || task.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        // if title value is empty
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        // save works for both adding and updating
        taskService.addTask(task);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Task> deleteById (@PathVariable Long id) {

        showMethodName("TaskController: deleteById() ---------------------------------------------------");
        // try-catch используется для отображения своего текста, а не stacktrace
        // try-catch is used for displaying its own text, not stacktrace
        try {
            taskService.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id " + id + " not found", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {

        showMethodName("TaskController: getById() ---------------------------------------------------");
        Task task;

        // try-catch используется для отображения своего текста, а не stacktrace
        // try-catch is used for displaying its own text, not stacktrace
        try {
            task = taskService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id " + id + " not found", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(task);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Task>> search (@RequestBody TaskSearchValues taskSearchValues) {

        showMethodName("TaskController: search() ---------------------------------------------------");

        String title = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;
        Integer completed = taskSearchValues.getCompleted() != null ? taskSearchValues.getCompleted() : null;
        Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;

        String sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : null;
        String sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortColumn() : null;

        Integer pageNumber = taskSearchValues.getPageNumber() != null ? taskSearchValues.getPageNumber() : null;
        Integer pageSize = taskSearchValues.getPageSize() != null ? taskSearchValues.getPageSize() : null;

        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 || sortDirection.trim().equals("ask") ? Sort.Direction.ASC : Sort.Direction.DESC;

        // объект сортировки
        // object for sorting
        Sort sort = Sort.by(direction, sortColumn);

        // объект для разбиения на страницы
        // object for pagination
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        // результат запроса с постраничным выводом
        // query result with paginated output
        Page result = taskService.findByParams(title, completed, priorityId, categoryId, pageRequest);

        return ResponseEntity.ok(result);
    }

}
