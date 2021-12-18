package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.dto.RoomDTO;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.PersonService;
import ru.job4j.chat.service.RoomService;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<List<RoomDTO>> findAll() {
        Iterable<Room> rooms = roomService.findAll();
        List<RoomDTO> roomsDTO = new ArrayList<>();
        rooms.forEach(room -> roomsDTO.add(
                RoomDTO.of(
                        room.getId(),
                        room.getName(),
                        room.getPerson().getId()
                ))
        );
        return new ResponseEntity<>(
                roomsDTO,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> findById(@PathVariable int id) {
        Room room = roomService.findById(id);
        if (room == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Комната не найдена!!!"
            );
        }
        RoomDTO roomDTO = RoomDTO.of(
                room.getId(),
                room.getName(),
                room.getPerson().getId()
        );
        return new ResponseEntity<>(
                roomDTO,
                HttpStatus.OK
        );
    }

    @PostMapping({"/", ""})
    public ResponseEntity<RoomDTO> create(@RequestBody RoomDTO roomDTO) {
        RoomDTO responseRoom = getResponseRoomDTO(roomDTO, 0);
        return new ResponseEntity<>(
                responseRoom,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> update(@PathVariable int id,
                                          @RequestBody RoomDTO roomDTO) {
        RoomDTO responseRoom = getResponseRoomDTO(roomDTO, id);
        return new ResponseEntity<>(
                responseRoom,
                HttpStatus.OK
        );
    }

    private RoomDTO getResponseRoomDTO(@RequestBody RoomDTO roomDTO, int id) {
        if (roomDTO.getName() == null) {
            throw new NullPointerException("Название комнаты не должно быть пустым!!!");
        }
        Person person = personService.findById(roomDTO.getPersonId());
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Пользователь не найден!!!");
        }
        Room room = Room.of(
                id,
                roomDTO.getName(),
                person
        );
        Room savedRoom = roomService.save(room);
        return RoomDTO.of(
                savedRoom.getId(),
                savedRoom.getName(),
                savedRoom.getPerson().getId()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        roomService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
