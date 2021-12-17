package ru.job4j.chat.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;

import java.util.Collections;

/**
 * Класс UserDetailsServiceImpl
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository;

    public UserDetailsServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username).orElse(null);
        if (person == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(person.getUsername(), person.getPassword(), Collections.emptyList());
    }
}
