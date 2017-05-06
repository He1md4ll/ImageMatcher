package edu.hsbremen.cloud.firebase.config.authentication;

import com.google.common.base.Preconditions;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {

    private Object principal;

    public FirebaseAuthenticationToken(Object principal) {
        super(null);
        Preconditions.checkNotNull(principal);
        this.principal = principal;
        setAuthenticated(Boolean.FALSE);
    }

    FirebaseAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        Preconditions.checkNotNull(principal);
        Preconditions.checkNotNull(authorities);
        this.principal = principal;
        setAuthenticated(Boolean.TRUE);
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        Preconditions.checkArgument(!authenticated, "Manipulation of authentication flag prohibited !!");
        super.setAuthenticated(false);
    }
}