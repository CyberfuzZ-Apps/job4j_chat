package ru.job4j.chat.service;

import ru.job4j.chat.model.Message;

import org.springframework.stereotype.Service;
import ru.job4j.chat.repository.MessageRepository;
import ru.job4j.chat.repository.RoomRepository;


/**
 * Класс MessageService
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
@Service
public class MessageService implements GlobalService<Message> {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Iterable<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message findById(int id) {
        return messageRepository.findById(id).orElse(null);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void deleteById(int id) {
        messageRepository.deleteById(id);
    }

    public Iterable<Message> findByRoomId(int id) {
        return messageRepository.findByRoomId(id);
    }
}
