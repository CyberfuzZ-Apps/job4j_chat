package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.PatchUtil;
import ru.job4j.chat.dto.MessageDTO;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.MessageService;
import ru.job4j.chat.service.PersonService;
import ru.job4j.chat.service.RoomService;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<List<MessageDTO>> findAllMessagesInRoom(@PathVariable int roomId) {
        Room room = roomService.findById(roomId);
        if (room == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Комната не найдена");
        }
        Iterable<Message> messages = messageService.findByRoomId(roomId);
        List<MessageDTO> messagesDTO = new ArrayList<>();
        messages.forEach(message -> messagesDTO.add(buildMessageDTO(
                message,
                message.getPerson().getId(),
                message.getRoom().getId())
        ));
        return new ResponseEntity<>(
                messagesDTO,
                HttpStatus.OK
        );
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<MessageDTO> findMessageById(@PathVariable int messageId,
                                                   @PathVariable int roomId) {
        Message message = messageService.findById(messageId);
        if (message == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Сообщение не найдено!!!"
            );
        }
        MessageDTO messageDTO = buildMessageDTO(message, message.getPerson().getId(), roomId);
        return new ResponseEntity<>(
                messageDTO,
                HttpStatus.OK
        );
    }

    @PostMapping({"/", ""})
    public ResponseEntity<MessageDTO> createMessage(@Valid @RequestBody MessageDTO messageDTO,
                                          @PathVariable int roomId) {
        messageDTO.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        Message message = buildMessage(messageDTO, 0);
        Message savedMessage = messageService.save(message);
        MessageDTO responseMessageDTO = buildMessageDTO(
                savedMessage,
                savedMessage.getPerson().getId(),
                savedMessage.getRoom().getId());
        return new ResponseEntity<>(
                responseMessageDTO,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<MessageDTO> updateMessage(@Valid @RequestBody MessageDTO messageDTO,
                                       @PathVariable int messageId,
                                          @PathVariable int roomId) {
        Message foundedMessage = getMessageToUpdate(messageDTO, messageId);
        Message message = buildMessage(messageDTO, messageId);
        message.setCreated(foundedMessage.getCreated());
        Message updatedMessage = messageService.save(message);
        MessageDTO responseMessageDTO = buildMessageDTO(
                updatedMessage,
                updatedMessage.getPerson().getId(),
                roomId);
        return new ResponseEntity<>(
                responseMessageDTO,
                HttpStatus.OK
        );
    }

    @PatchMapping("/{messageId}")
    public ResponseEntity<MessageDTO> patchMessage(@RequestBody MessageDTO messageDTO,
                                                    @PathVariable int messageId,
                                                    @PathVariable int roomId)
            throws InvocationTargetException, IllegalAccessException {
        Message foundedMessage = getMessageToUpdate(messageDTO, messageId);
        messageDTO.setPersonId(foundedMessage.getPerson().getId());
        messageDTO.setRoomId(foundedMessage.getRoom().getId());
        Message message = buildMessage(messageDTO, messageId);
        message.setCreated(foundedMessage.getCreated());
        Message patchedMessage = PatchUtil.patch(message, foundedMessage);
        Message responseMessage = messageService.save(patchedMessage);
        MessageDTO responseMessageDTO = buildMessageDTO(
                responseMessage,
                responseMessage.getPerson().getId(),
                roomId);
        return new ResponseEntity<>(
                responseMessageDTO,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int messageId) {
        messageService.deleteById(messageId);
        return ResponseEntity.ok().build();
    }

    private Message getMessageToUpdate(MessageDTO messageDTO, int messageId) {
        if (messageDTO.getMessage() == null) {
            throw new NullPointerException("Сообщение не должно быть пустым!!!");
        }
        Message foundedMessage = messageService.findById(messageId);
        if (foundedMessage == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Сообщение не найдено!!!");
        }
        return foundedMessage;
    }

    private MessageDTO buildMessageDTO(Message message, int personId, int roomId) {
        return MessageDTO.of(
                message.getId(),
                message.getMessage(),
                message.getCreated(),
                personId,
                roomId
        );
    }

    private Message buildMessage(MessageDTO messageDTO, int id) {
        Person person = personService.findById(messageDTO.getPersonId());
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Пользователь не найден!!!");
        }
        Room room = roomService.findById(messageDTO.getRoomId());
        if (room == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Комната не найдена!!!"
            );
        }
        return Message.of(
                id,
                messageDTO.getMessage(),
                messageDTO.getCreated(),
                person,
                room
        );
    }
}
