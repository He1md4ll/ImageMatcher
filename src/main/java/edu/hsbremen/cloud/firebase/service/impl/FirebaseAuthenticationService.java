package edu.hsbremen.cloud.firebase.service.impl;

import com.google.common.base.Preconditions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.Tasks;
import edu.hsbremen.cloud.exception.FirebaseTokenInvalidException;
import edu.hsbremen.cloud.firebase.service.IFirebaseAuthenticationService;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FirebaseAuthenticationService implements IFirebaseAuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirebaseAuthenticationService.class.getSimpleName());
    @Override
    public FirebaseToken verify(String tokenID) {
        Preconditions.checkArgument(!StringUtil.isNullOrEmpty(tokenID), "Token can not be blank");
        try {
            Task<FirebaseToken> authTask = FirebaseAuth.getInstance().verifyIdToken(tokenID);
            Tasks.await(authTask);
            return authTask.getResult();
        } catch (Exception e) {
            LOGGER.warn("Firebase token invalid: " + e.getMessage(), e);
            throw new FirebaseTokenInvalidException(e);
        }
    }
}