package ru.job4j.chat.service;

/**
 * Класс GlobalService
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
public interface GlobalService<T> {

    Iterable<T> findAll();

    T findById(int id);

    T save(T t);

    void deleteById(int id);

}
