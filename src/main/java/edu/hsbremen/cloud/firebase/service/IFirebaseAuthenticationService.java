package edu.hsbremen.cloud.firebase.service;

import com.google.firebase.auth.FirebaseToken;

public interface IFirebaseAuthenticationService {
    FirebaseToken verify(String tokenID);
}