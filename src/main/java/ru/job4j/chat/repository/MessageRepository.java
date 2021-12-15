package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Message;

import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    @Query(value = "from Message m where m.room.id = ?1")
    Iterable<Message> findByRoomId(int roomId);

}