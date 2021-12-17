package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.exception.IllegalAuthorityException;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.service.RoleService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

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
    private final ObjectMapper objectMapper;

    public RoleController(RoleService roleService, ObjectMapper objectMapper) {
        this.roleService = roleService;
        this.objectMapper = objectMapper;
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
        if (role == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Роль не найдена!!!"
            );
        }
        return new ResponseEntity<>(
                role,
                HttpStatus.OK
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
        if (role.getAuthority() == null) {
            throw new NullPointerException("Название роли не должно быть пустым!!!");
        }
        if (!role.getAuthority().startsWith("ROLE_")) {
            throw new IllegalAuthorityException(
                    "Название роли должно иметь префикс 'ROLE_'");
        }
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

    @ExceptionHandler(value = {IllegalAuthorityException.class})
    public void exceptionHandler(Exception e,
                                 HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
    }
}
