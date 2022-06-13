package com.example.rsoi_course_work.front.model.user.security;

import com.example.rsoi_course_work.front.model.user.UserRole;

import java.util.Objects;
import java.util.UUID;

public class SessionJwtResponse {
    private String jwt;
    private UUID user_uid;
    private UserRole role;

    public SessionJwtResponse() {
    }

    public SessionJwtResponse(String jwt, UUID user_uid, UserRole role) {
        this.jwt = jwt;
        this.user_uid = user_uid;
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UUID getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(UUID user_uid) {
        this.user_uid = user_uid;
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

        SessionJwtResponse that = (SessionJwtResponse) o;

        if (!Objects.equals(jwt, that.jwt)) return false;
        if (!Objects.equals(user_uid, that.user_uid)) return false;
        return role == that.role;
    }

    @Override
    public int hashCode() {
        int result = jwt != null ? jwt.hashCode() : 0;
        result = 31 * result + (user_uid != null ? user_uid.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SessionJwtResponse{" +
                "jwt='" + jwt + '\'' +
                ", user_uid=" + user_uid +
                ", role=" + role +
                '}';
    }
}
