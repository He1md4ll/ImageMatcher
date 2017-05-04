package edu.hsbremen.cloud.dto;

public class RegisterUserDto {
    private String username;
    private String password;
    private String eMail;

    public RegisterUserDto(String username, String password, String eMail) {
        this.username = username;
        this.password = password;
        this.eMail = eMail;
    }

    public String getUsername() {
        return username;
    }

    public String getEMail() {
        return eMail;
    }

    public String getPassword() {

        return password;
    }
}