package com.alexfrolov.tasklist.backendspringboot.search;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

// возможные значения, по которым можно искать категории
// possible values by which we can search for categories
public class CategorySearchValues {

    private String text;

}
