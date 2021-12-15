package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.service.RoleService;

/**
 * Класс RoleController
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping({"/", ""})
    public ResponseEntity<Iterable<Role>> findAll() {
        return new ResponseEntity<>(
                roleService.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable int id) {
        Role role = roleService.findById(id);
        return new ResponseEntity<>(
                role,
                role != null ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/authority/{authority}")
    public ResponseEntity<Role> findByAuthority(@PathVariable String authority) {
        Role role = roleService.findByAuthority(authority);
        return new ResponseEntity<>(
                role,
                role != null ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping({"/", ""})
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return new ResponseEntity<>(
                roleService.save(role),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable int id,
                                               @RequestBody Role role) {
        role.setId(id);
        Role role1 = roleService.save(role);
        return new ResponseEntity<>(
                role1,
                role.getId() == role1.getId() ? HttpStatus.OK : HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        roleService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/authority/{authority}")
    public ResponseEntity<Void> deleteByAuthority(@PathVariable String authority) {
        roleService.deleteByAuthority(authority);
        return ResponseEntity.ok().build();
    }
}
