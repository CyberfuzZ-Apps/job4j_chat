package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;

/**
 * Класс PersonService
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
@Service
public class PersonService implements GlobalService<Person> {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person findById(int id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void deleteById(int id) {
        personRepository.deleteById(id);
    }

    public Person findByUsername(String username) {
        return personRepository.findByUsername(username).orElse(null);
    }

    public void deleteByUsername(String username) {
        personRepository.deleteByUsername(username);
    }
}
