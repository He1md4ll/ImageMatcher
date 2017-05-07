package edu.hsbremen.cloud.dto;

import edu.hsbremen.cloud.persistance.domain.UserEntity;

public class UserDto {
    private String username;
    private String eMail;

    public UserDto(String username, String eMail) {
        this.username = username;
        this.eMail = eMail;
    }

    public static UserDto fromUserEntity(final UserEntity userEntity) {
        return new UserDto(userEntity.getUsername(), userEntity.getEmail());
    }

    public String getUsername() {
        return username;
    }

    public String geteMail() {
        return eMail;
    }
}