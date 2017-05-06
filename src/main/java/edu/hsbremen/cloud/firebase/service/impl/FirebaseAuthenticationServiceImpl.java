package edu.hsbremen.cloud.firebase.service.impl;

import com.google.common.base.Preconditions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.Tasks;
import edu.hsbremen.cloud.exception.FirebaseTokenInvalidException;
import edu.hsbremen.cloud.firebase.service.FirebaseAuthenticationService;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

@Service
public class FirebaseAuthenticationServiceImpl implements FirebaseAuthenticationService {

    @Override
    public FirebaseToken verify(String tokenID) {
        Preconditions.checkArgument(!StringUtil.isNullOrEmpty(tokenID), "Token can not be blank");
        try {
            Task<FirebaseToken> authTask = FirebaseAuth.getInstance().verifyIdToken(tokenID);
            Tasks.await(authTask);
            return authTask.getResult();
        } catch (Exception e) {
            throw new FirebaseTokenInvalidException(e);
        }
    }
}