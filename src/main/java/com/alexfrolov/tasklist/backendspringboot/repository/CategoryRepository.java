package com.alexfrolov.tasklist.backendspringboot.repository;

import com.alexfrolov.tasklist.backendspringboot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
// OOP principle: abstraction-implementation - here we describe all available ways to access data
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // если передали пустое значение title, то получим все значения
    // if title value is empty, we get all values
    @Query("SELECT c FROM Category c where :title is null or :title='' or lower(c.title) like lower(concat('%', :title, '%')) order by c.title asc")
    List<Category> findByTitle(@Param("title") String title);

    // получение всех значений, сортированных по названию
    // getting all values sorted by title
    List<Category> findAllByOrderByTitleAsc();
}
