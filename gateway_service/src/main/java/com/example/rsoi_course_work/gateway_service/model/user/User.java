package com.example.rsoi_course_work.gateway_service.model.user;

import java.util.Objects;
import java.util.UUID;

public class User {
    private Long id;
    private UUID user_uid;
    private String name;
    private String surname;
    private String login;
    private String password;
    private UserRole role;

    public User() {
    }

    public User(Long id, UUID user_uid, String name, String surname, String login, String password, UserRole role) {
        this.id = id;
        this.user_uid = user_uid;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(UUID user_uid, String name, String surname, String login, String password, UserRole role) {
        this.user_uid = user_uid;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public UserInfo getInfo() {
        return new UserInfo(user_uid, name, surname, login, role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(UUID user_uid) {
        this.user_uid = user_uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(user_uid, user.user_uid)) return false;
        if (!Objects.equals(name, user.name)) return false;
        if (!Objects.equals(surname, user.surname)) return false;
        if (!Objects.equals(login, user.login)) return false;
        if (!Objects.equals(password, user.password)) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user_uid != null ? user_uid.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_uid=" + user_uid +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}

