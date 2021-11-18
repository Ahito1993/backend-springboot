package com.alexfrolov.tasklist.backendspringboot.controller;

import com.alexfrolov.tasklist.backendspringboot.entity.Stat;
import com.alexfrolov.tasklist.backendspringboot.repository.StatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.alexfrolov.tasklist.backendspringboot.util.MyLogger.showMethodName;

// используем @RestController вместо обычного @Controller, чтобы все ответы сразу оборачивались в JSON
// we use @RestController instead of the usual @Controller so that all responses are immediately wrapped in JSON
@RestController
public class StatController {

    private final StatRepository statRepository;

    // id всегда равен 1
    // id always 1
    private final long defaultId = 1L;

    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    // automatic implementation of a class instance via the constructor
    // we don't use @Autowired for a class variable, because "Field injection is not recommended "
    public StatController(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    @GetMapping("/stat")
    public ResponseEntity<Stat> getById () {

        showMethodName("StatController: getById() ---------------------------------------------------");
        return ResponseEntity.ok(statRepository.findById(defaultId).get());
    }


}
