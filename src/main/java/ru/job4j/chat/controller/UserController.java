package ru.job4j.chat.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.service.PersonService;
import ru.job4j.chat.service.RoleService;

/**
 * Класс UserController
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
@RestController
@RequestMapping("users")
public class UserController {

    private final PersonService personService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;

    public UserController(PersonService personService,
                          RoleService roleService,
                          BCryptPasswordEncoder encoder) {
        this.personService = personService;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @GetMapping("/all")
    public Iterable<Person> findAll() {
        return personService.findAll();
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person person) {
        if (person.getNickname() == null
                || person.getUsername() == null
                || person.getPassword() == null) {
            throw new NullPointerException("Ник, имя пользователя и пароль "
                    + "не должны быть пустыми!!!");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        person.setAuthority(roleService.findByAuthority("ROLE_USER"));
        personService.save(person);
    }
}
