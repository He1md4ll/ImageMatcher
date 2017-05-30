package edu.hsbremen.cloud.dto;

import com.google.firebase.auth.FirebaseToken;

import java.util.Optional;

public class RegisterUserDto {
    private String uid;
    private String name;
    private String eMail;

    public RegisterUserDto(String uid, String name, String eMail) {
        this.uid = uid;
        this.name = name;
        this.eMail = eMail;
    }

    public static RegisterUserDto fromFirebaseToken(FirebaseToken firebaseToken) {
        return new RegisterUserDto(firebaseToken.getUid(),
                Optional.ofNullable(firebaseToken.getName()).orElse(firebaseToken.getUid()),
                firebaseToken.getEmail());
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String geteMail() {
        return eMail;
    }
}