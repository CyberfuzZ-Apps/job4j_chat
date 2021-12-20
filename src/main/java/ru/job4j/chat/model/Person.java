package ru.job4j.chat.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 * Класс Person
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PositiveOrZero(message = "id должен быть положительным числом или 0!")
    private int id;

    @NotBlank(message = "username не должен быть пустым!")
    private String nickname;

    @NotBlank(message = "username не должен быть пустым")
    @Email(message = "username должен содержать адрес электронной почты с символом '@'")
    private String username;

    @NotBlank(message = "password не должен быть пустым!")
    private String password;

    @ManyToOne
    private Role authority;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getAuthority() {
        return authority;
    }

    public void setAuthority(Role authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{"
                + "id=" + id
                + ", nickname='" + nickname + '\''
                + ", username='" + username + '\''
                + ", authority=" + authority
                + '}';
    }
}
