package edu.hsbremen.cloud.config;

import edu.hsbremen.cloud.exception.ExceptionEntryPoint;
import edu.hsbremen.cloud.firebase.config.authentication.FirebaseAuthenticationFilter;
import edu.hsbremen.cloud.firebase.config.authentication.FirebaseAuthenticationProvider;
import edu.hsbremen.cloud.firebase.service.IFirebaseAuthenticationService;
import edu.hsbremen.cloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

    public static class Roles {
        public static final String ANONYMOUS = "ANONYMOUS";
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";

        private static final String ROLE_ = "ROLE_";
        public static final String ROLE_ANONYMOUS = ROLE_ + ANONYMOUS;
        public static final String ROLE_USER = ROLE_ + USER;
        public static final String ROLE_ADMIN = ROLE_ + ADMIN;
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Configuration
    protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        private IUserService userService;

        @Autowired
        private FirebaseAuthenticationProvider firebaseAuthenticationProvider;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService);
            auth.authenticationProvider(firebaseAuthenticationProvider);
        }
    }

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    @Configuration
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Autowired
        private IFirebaseAuthenticationService firebaseAuthenticationService;
        private AuthenticationEntryPoint entryPoint  = new ExceptionEntryPoint();

        private FirebaseAuthenticationFilter getFirebaseAuthenticationFilter() {
            return new FirebaseAuthenticationFilter(firebaseAuthenticationService, entryPoint);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            // Ignore swagger and configuration paths for discovery
            web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources",
                    "/configuration/security", "/swagger-ui.html", "/webjars/**", "/v2/swagger.json");
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // TODO: Disable session????
            // Info: Access to api is configured via PreAuthorize and PostAuthorize
            http
                    .addFilterBefore(getFirebaseAuthenticationFilter(), BasicAuthenticationFilter.class).authorizeRequests()
                    .antMatchers("/api/**").hasAnyRole(Roles.ANONYMOUS, Roles.USER, Roles.ADMIN)
                    .antMatchers("/**").denyAll()
                    .and().csrf().disable()
                    .anonymous().authorities(Roles.ROLE_ANONYMOUS)
                    .and().exceptionHandling().authenticationEntryPoint(entryPoint);
        }
    }
}