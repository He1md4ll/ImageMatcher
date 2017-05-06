package edu.hsbremen.cloud.firebase.config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${custom.firebase.database.url}")
    private String databaseUrl;

    @Value("${custom.firebase.config.path}")
    private String configPath;

    @PostConstruct
    public void init() {
        /**
         * https://firebase.google.com/docs/server/setup
         *
         * Create service account , download json
         */
        final InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream(configPath);
        final FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                .setDatabaseUrl(databaseUrl)
                .build();
        FirebaseApp.initializeApp(options);
    }
}