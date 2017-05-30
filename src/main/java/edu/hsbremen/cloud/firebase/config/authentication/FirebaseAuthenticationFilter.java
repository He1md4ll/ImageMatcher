package edu.hsbremen.cloud.firebase.config.authentication;

import com.google.common.base.Preconditions;
import com.google.firebase.auth.FirebaseToken;
import edu.hsbremen.cloud.firebase.service.IFirebaseAuthenticationService;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirebaseAuthenticationFilter.class);
    private static final String AUTH_HEADER = "X-Authorization-Firebase";
    private static final String SESSION_AUTH_ATTR = "FIREBASE_AUTH";
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
                Authentication auth;
                //TODO: Deal with firebase token expiration
                final HttpSession session = httpServletRequest.getSession();
                final Object sessionAuth = session.getAttribute(SESSION_AUTH_ATTR);
                if (!session.isNew() && sessionAuth != null) {
                    LOGGER.info("Using authentication from persisted valid session.");
                    auth = (Authentication) sessionAuth;
                } else {
                    LOGGER.info("No auth in session. Processing firebase token authentication.");
                    final FirebaseToken firebaseToken = firebaseAuthenticationService.verify(token);
                    auth = new FirebaseAuthenticationToken(firebaseToken.getUid());
                    session.setAttribute(SESSION_AUTH_ATTR, auth);
                }
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } catch (AuthenticationException e) {
                entryPoint.commence(httpServletRequest, httpServletResponse, e);
            }
        }
    }
}