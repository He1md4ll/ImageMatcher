package edu.hsbremen.cloud.exception;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public class FirebaseUserInvalidException extends AuthenticationCredentialsNotFoundException {
    private static final String MESSAGE = "User not registered. Please sign up first.";

    public FirebaseUserInvalidException() {
        super(MESSAGE);
    }
}