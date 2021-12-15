package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.service.PersonService;

/**
 * Класс PersonController
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping({"/", ""})
    public ResponseEntity<Iterable<Person>> findAll() {
        return new ResponseEntity<>(
                personService.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Person person = personService.findById(id);
        return new ResponseEntity<>(
                person,
                person != null ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Person> findByUsername(@PathVariable String username) {
        Person person = personService.findByUsername(username);
        return new ResponseEntity<>(
                person,
                person != null ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping({"/", ""})
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        return new ResponseEntity<>(
                personService.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id,
                                               @RequestBody Person person) {
        person.setId(id);
        Person person1 = personService.save(person);
        return new ResponseEntity<>(
                person1,
                person.getId() == person1.getId() ? HttpStatus.OK : HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<Void> deleteByUsername(@PathVariable String username) {
        personService.deleteByUsername(username);
        return ResponseEntity.ok().build();
    }
}
