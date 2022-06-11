/*
package com.example.rsoi_course_work.session_service.security;

import com.example.rsoi_course_work.session_service.model.UserRole;

import java.util.Objects;
import java.util.UUID;

public class SessionUserResponse {
    private Long id;

    private UUID user_uid;

    private String name;

    private String surname;

    private String login;

    private String password;

    private UserRole role;

    private String jwt;

    public SessionUserResponse() {
    }

    public SessionUserResponse(Long id, UUID user_uid, String name, String surname, String login, String password, UserRole role, String jwt) {
        this.id = id;
        this.user_uid = user_uid;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.role = role;
        this.jwt = jwt;
    }

    public SessionUserResponse(UUID user_uid, String name, String surname, String login, String password, UserRole role, String jwt) {
        this.user_uid = user_uid;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.role = role;
        this.jwt = jwt;
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

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionUserResponse that = (SessionUserResponse) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(user_uid, that.user_uid)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(surname, that.surname)) return false;
        if (!Objects.equals(login, that.login)) return false;
        if (!Objects.equals(password, that.password)) return false;
        if (role != that.role) return false;
        return Objects.equals(jwt, that.jwt);
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
        result = 31 * result + (jwt != null ? jwt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SessionUserResponse{" +
                "id=" + id +
                ", user_uid=" + user_uid +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", jwt='" + jwt + '\'' +
                '}';
    }
}


*/
