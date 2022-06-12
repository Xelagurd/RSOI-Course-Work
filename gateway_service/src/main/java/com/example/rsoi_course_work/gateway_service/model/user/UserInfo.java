package com.example.rsoi_course_work.gateway_service.model.user;


import java.util.Objects;
import java.util.UUID;

public class UserInfo {
    private UUID user_uid;
    private String name;
    private String surname;
    private String login;
    private UserRole role;

    public UserInfo() {
    }

    public UserInfo(User user) {
        this.user_uid = user.getUser_uid();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.login = user.getLogin();
        this.role = user.getRole();
    }

    public UserInfo(UUID user_uid, String name, String surname, String login, UserRole role) {
        this.user_uid = user_uid;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.role = role;
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

        UserInfo userInfo = (UserInfo) o;

        if (!Objects.equals(user_uid, userInfo.user_uid)) return false;
        if (!Objects.equals(name, userInfo.name)) return false;
        if (!Objects.equals(surname, userInfo.surname)) return false;
        if (!Objects.equals(login, userInfo.login)) return false;
        return role == userInfo.role;
    }

    @Override
    public int hashCode() {
        int result = user_uid != null ? user_uid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "user_uid=" + user_uid +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", role=" + role +
                '}';
    }
}