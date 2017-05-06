package edu.hsbremen.cloud.firebase.service;

import com.google.firebase.auth.FirebaseToken;

public interface FirebaseAuthenticationService {
    FirebaseToken verify(String tokenID);
}