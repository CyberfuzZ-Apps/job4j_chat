package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.MessageService;
import ru.job4j.chat.service.PersonService;
import ru.job4j.chat.service.RoomService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Класс MessageController
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
@RestController
@RequestMapping("/room/{roomId}/message")
public class MessageController {

    private final MessageService messageService;
    private final RoomService roomService;
    private final PersonService personService;

    public MessageController(MessageService messageService, RoomService roomService, PersonService personService) {
        this.messageService = messageService;
        this.roomService = roomService;
        this.personService = personService;
    }

    @GetMapping({"/", ""})
    public List<Message> findAllMessagesInRoom(@PathVariable int roomId) {
        return StreamSupport.stream(
                messageService.findByRoomId(roomId).spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> findMessageById(@PathVariable int messageId) {
        Message message = messageService.findById(messageId);
        if (message == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Сообщение не найдено!!!"
            );
        }
        return new ResponseEntity<>(
                message,
                HttpStatus.OK
        );
    }

    @PostMapping({"/", ""})
    public ResponseEntity<Message> create(@RequestBody Message message,
                                          @PathVariable int roomId) {
        buildMessage(message, roomId);
        return new ResponseEntity<>(
                messageService.save(message),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<Message> update(@RequestBody Message message,
                                       @PathVariable int messageId,
                                          @PathVariable int roomId) {
        buildMessage(message, roomId);
        message.setId(messageId);
        Message message1 = messageService.save(message);
        return new ResponseEntity<>(
                message1,
                message.getId() == message1.getId() ? HttpStatus.OK : HttpStatus.CREATED
        );
    }

    private void buildMessage(@RequestBody Message message, @PathVariable int roomId) {
        if (message.getMessage() == null) {
            throw new NullPointerException("Сообщение не должно быть пустым!!!");
        }
        message.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        Room room = roomService.findById(roomId);
        Person person = personService.findById(message.getPerson().getId());
        message.setRoom(room);
        message.setPerson(person);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> delete(@PathVariable int messageId) {
        messageService.deleteById(messageId);
        return ResponseEntity.ok().build();
    }
}
