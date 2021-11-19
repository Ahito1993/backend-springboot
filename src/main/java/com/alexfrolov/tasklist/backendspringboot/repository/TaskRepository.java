package com.alexfrolov.tasklist.backendspringboot.repository;

import com.alexfrolov.tasklist.backendspringboot.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
// OOP principle: abstraction-implementation - here we describe all available ways to access data
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // поиск по все параметрам (пустые не учитываются)
    // search by all params (empty params are not taken into account)
    @Query("select t from Task t where " +
            "(:title is null or :title='' or lower(t.title) like lower(concat('%', :title, '%'))) and" +
            "(:completed is null or t.completed=:completed) and" +
            "(:priorityId is null or t.priority.id=:priorityId) and" +
            "(:categoryId is null or t.category.id=:categoryId)")
    Page<Task> findByParams (@Param("title") String title
            , @Param("completed") Integer completed
            , @Param("priorityId") Long priorityId
            , @Param("categoryId") Long categoryId
            , Pageable pageable);
}
