package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.util.List;

/**
 * Класс RoomService
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
@Service
public class RoomService implements GlobalService<Room> {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Iterable<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room findById(int id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void deleteById(int id) {
        roomRepository.deleteById(id);
    }

    public List<Room> findByName(String name) {
        return roomRepository.findByName(name);
    }
}
