package ru.job4j.chat.dto;

import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Room;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

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

}
