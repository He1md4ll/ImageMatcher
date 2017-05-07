package edu.hsbremen.cloud.dto;

import com.google.firebase.auth.FirebaseToken;

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
        return new RegisterUserDto(firebaseToken.getUid(), firebaseToken.getName(), firebaseToken.getEmail());
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