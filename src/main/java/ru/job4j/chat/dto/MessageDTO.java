package ru.job4j.chat.dto;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Класс MessageDTO
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
public class MessageDTO {

    private int id;
    private String message;
    private Timestamp created;
    private int personId;
    private int roomId;

    public static MessageDTO of(int id, String message, Timestamp created,
                                int personId, int roomId) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.id = id;
        messageDTO.message = message;
        messageDTO.created = created;
        messageDTO.personId = personId;
        messageDTO.roomId = roomId;
        return messageDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageDTO that = (MessageDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
