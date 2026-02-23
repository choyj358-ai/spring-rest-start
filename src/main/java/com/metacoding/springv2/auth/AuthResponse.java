package com.metacoding.springv2.auth;

import com.metacoding.springv2.user.User;

import lombok.Data;

public class AuthResponse {

    @Data
    public static class DTO {
        private Integer id;
        private String username;
        private String roles;

        public DTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.roles = user.getRoles();
        }

    }

    @Data
    public static class CheckUsernameDTO {
        private boolean isAvailable;
        private String message;

        public CheckUsernameDTO(boolean isAvailable, String message) {
            this.isAvailable = isAvailable;
            this.message = message;
        }
    }
}