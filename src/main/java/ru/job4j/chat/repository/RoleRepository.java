package ru.job4j.chat.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Role;

import javax.transaction.Transactional;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByAuthority(String authority);

    @Transactional
    void deleteByAuthority(String authority);
}