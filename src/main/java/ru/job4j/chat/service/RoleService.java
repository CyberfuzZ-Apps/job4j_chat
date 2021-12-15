package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.RoleRepository;

/**
 * Класс RoleService
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
@Service
public class RoleService implements GlobalService<Role> {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteById(int id) {
        roleRepository.deleteById(id);
    }

    public Role findByAuthority(String authority) {
        return roleRepository.findByAuthority(authority).orElse(null);
    }

    public void deleteByAuthority(String authority) {
        roleRepository.deleteByAuthority(authority);
    }
}
