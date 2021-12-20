package ru.job4j.chat.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Класс MessageDTO
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
public class MessageDTO {

    @PositiveOrZero(message = "id должен быть положительным числом или 0!")
    private int id;

    @NotBlank(message = "message не может быть пустым!")
    private String message;

    private Timestamp created;

    @Positive(message = "personId должен быть положительным числом и не 0!")
    private int personId;

    @Positive(message = "roomId должен быть положительным числом и не 0!")
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
