package com.metacoding.springv2.user;

import lombok.Getter;
import lombok.Setter;

public class UserResponse {

    @Getter
    @Setter
    public static class UserFindByIdDTO {
        private Integer id;
        private String username;
        private String email;

        public UserFindByIdDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }
}
