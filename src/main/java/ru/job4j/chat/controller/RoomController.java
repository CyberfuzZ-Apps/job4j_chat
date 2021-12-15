package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.PersonService;
import ru.job4j.chat.service.RoomService;

/**
 * Класс RoomController
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;
    private final PersonService personService;

    public RoomController(RoomService roomService, PersonService personService) {
        this.roomService = roomService;
        this.personService = personService;
    }

    @GetMapping({"/", ""})
    public ResponseEntity<Iterable<Room>> findAll() {
        Iterable<Room> rooms = roomService.findAll();
        return new ResponseEntity<>(
                rooms,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        Room room = roomService.findById(id);
        return new ResponseEntity<>(
                room,
                room != null ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping({"/", ""})
    public ResponseEntity<Room> create(@RequestBody Room room) {
        int personId = room.getPerson().getId();
        Person person = personService.findById(personId);
        room.setPerson(person);
        return new ResponseEntity<>(
                roomService.save(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> update(@PathVariable int id,
            @RequestBody Room room) {
        room.setId(id);
        Room room1 = roomService.save(room);
        return new ResponseEntity<>(
                room1,
                room.getId() == room1.getId() ? HttpStatus.OK : HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        roomService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
