package ru.job4j.chat.dto;

import java.util.Objects;

/**
 * Класс PersonDTO
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
public class PersonDTO {

    private int id;
    private String nickname;
    private String username;
    private String password;
    private int roleId;

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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonDTO personDTO = (PersonDTO) o;
        return id == personDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
