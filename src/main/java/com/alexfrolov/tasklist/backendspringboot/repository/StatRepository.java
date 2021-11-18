package com.alexfrolov.tasklist.backendspringboot.repository;

import com.alexfrolov.tasklist.backendspringboot.entity.Stat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
// OOP principle: abstraction-implementation - here we describe all available ways to access data
@Repository
public interface StatRepository extends CrudRepository<Stat, Long> {
}
