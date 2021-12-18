package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.dto.PersonDTO;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.service.PersonService;
import ru.job4j.chat.service.RoleService;

import java.util.ArrayList;
import java.util.List;

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
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;

    public PersonController(PersonService personService,
                            RoleService roleService,
                            BCryptPasswordEncoder encoder) {
        this.personService = personService;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @GetMapping({"/", ""})
    public ResponseEntity<List<PersonDTO>> findAll() {
        Iterable<Person> allPersons = personService.findAll();
        List<PersonDTO> personsDTO = new ArrayList<>();
        allPersons.forEach(person -> personsDTO.add(buildPersonDTO(person, person.getId())));
        return new ResponseEntity<>(
                personsDTO,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable int id) {
        Person person = personService.findById(id);
        if (person == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Пользователь не найден!!!"
            );
        }
        PersonDTO personDTO = buildPersonDTO(person, id);
        return new ResponseEntity<>(
                personDTO,
                HttpStatus.OK
        );
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<PersonDTO> findByUsername(@PathVariable String username) {
        Person person = personService.findByUsername(username);
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Пользователь не найден!!!");
        }
        PersonDTO personDTO = buildPersonDTO(person, person.getId());
        return new ResponseEntity<>(
                personDTO,
                HttpStatus.OK
        );
    }

    @PostMapping("/sign-up")
    public ResponseEntity<PersonDTO> signUp(@RequestBody Person person) {
        if (person.getNickname() == null
                || person.getUsername() == null
                || person.getPassword() == null) {
            throw new NullPointerException("Ник, имя пользователя и пароль "
                    + "не должны быть пустыми!!!");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        person.setAuthority(roleService.findByAuthority("ROLE_USER"));
        Person savedPerson = personService.save(person);
        return new ResponseEntity<>(
                buildPersonDTO(savedPerson, savedPerson.getId()),
                HttpStatus.CREATED
        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable int id,
                                               @RequestBody Person person) {
        if (personService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Пользователь не найден!!!");
        }
        person.setId(id);
        person.setPassword(encoder.encode(person.getPassword()));
        person.setAuthority(roleService.findByAuthority("ROLE_USER"));
        Person savedPerson = personService.save(person);
        return new ResponseEntity<>(
                buildPersonDTO(savedPerson, id),
                HttpStatus.OK
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

    private PersonDTO buildPersonDTO(Person person, int id) {
        return PersonDTO.of(
                id,
                person.getNickname(),
                person.getUsername(),
                person.getPassword(),
                person.getAuthority().getAuthority()
        );
    }
}
