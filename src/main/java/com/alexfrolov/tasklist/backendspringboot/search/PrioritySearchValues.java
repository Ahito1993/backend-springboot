package com.alexfrolov.tasklist.backendspringboot.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

// возможные значения, по которым можно искать приоритеты
// possible values by which we can search for priorities
public class PrioritySearchValues {
    private String text;
}
