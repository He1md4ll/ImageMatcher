package edu.hsbremen.cloud.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class FirebaseTokenInvalidException extends BadCredentialsException {
    private static final String MESSAGE = "FirebaseToken invalid";

    public FirebaseTokenInvalidException(Throwable cause) {
        super(MESSAGE, cause);
    }
}