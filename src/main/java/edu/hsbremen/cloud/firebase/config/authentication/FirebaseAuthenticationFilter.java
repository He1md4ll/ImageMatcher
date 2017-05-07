package edu.hsbremen.cloud.firebase.config.authentication;

import com.google.common.base.Preconditions;
import com.google.firebase.auth.FirebaseToken;
import edu.hsbremen.cloud.firebase.service.IFirebaseAuthenticationService;
import io.netty.util.internal.StringUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "X-Authorization-Firebase";
    private IFirebaseAuthenticationService firebaseAuthenticationService;
    private AuthenticationEntryPoint entryPoint;

    public FirebaseAuthenticationFilter(IFirebaseAuthenticationService firebaseAuthenticationService, AuthenticationEntryPoint entryPoint) {
        Preconditions.checkNotNull(firebaseAuthenticationService);
        Preconditions.checkNotNull(entryPoint);
        this.entryPoint = entryPoint;
        this.firebaseAuthenticationService = firebaseAuthenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String token = httpServletRequest.getHeader(AUTH_HEADER);
        if (StringUtil.isNullOrEmpty(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            try {
                final FirebaseToken firebaseToken = firebaseAuthenticationService.verify(token);
                final Authentication auth = new FirebaseAuthenticationToken(firebaseToken.getUid());
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } catch (AuthenticationException e) {
                entryPoint.commence(httpServletRequest, httpServletResponse, e);
            }
        }
    }
}