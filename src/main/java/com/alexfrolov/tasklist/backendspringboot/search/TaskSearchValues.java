package com.alexfrolov.tasklist.backendspringboot.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

// возможные значения, по которым можно искать задачи
// possible values by which we can search for tasks
public class TaskSearchValues {

    // все типы объектные для возможности передачи null
    // all types are object-based to be able to pass null
    private String title;
    private Integer completed;
    private Long priorityId;
    private Long categoryId;

    // постраничность
    // pagination
    private Integer pageNumber;
    private Integer pageSize;

    // сортировка
    // sorting
    private String sortColumn;
    private String sortDirection;


}
