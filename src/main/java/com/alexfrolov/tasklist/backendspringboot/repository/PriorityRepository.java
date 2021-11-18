package com.alexfrolov.tasklist.backendspringboot.repository;

import com.alexfrolov.tasklist.backendspringboot.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
// OOP principle: abstraction-implementation - here we describe all available ways to access data
@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    // получение всех значений, сортированных по id
    // getting all values sorted by id
    List<Priority> findAllByOrderByIdAsc();

    // если передали пустое значение title, то получим все значения
    // if title value is empty, we get all values
    @Query("SELECT c From Priority c where :title is null or :title='' or lower(c.title) like lower(concat('%', :title, '%')) order by c.title asc ")
    List<Priority> findByTitle(@Param("title") String title);
}
