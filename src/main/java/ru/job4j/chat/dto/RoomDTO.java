package ru.job4j.chat.dto;

import ru.job4j.chat.model.Person;

import java.util.Objects;

/**
 * Класс RoomDTO
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
public class RoomDTO {

    private int id;
    private String name;
    private int personId;

    public static RoomDTO of(int id, String name, int personId) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.id = id;
        roomDTO.name = name;
        roomDTO.personId = personId;
        return roomDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoomDTO roomDTO = (RoomDTO) o;
        return id == roomDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
