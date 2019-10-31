package pl.progser.api;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Component
public final class GoogleAuthorize {

    private final static String APPLICATION_NAME = "Progser";
    private final static String CREDENTIALS_FILE_PATH = "/credentials.json";
    private final static String TOKENS_DIRECTORY_PATH = "tokens";

    public static Credential authorize(String spreadsheetId) throws IOException, GeneralSecurityException {

        var in = Optional
                .ofNullable(GoogleAuthorize.class.getResourceAsStream(CREDENTIALS_FILE_PATH))
                .orElseThrow(() -> new FileNotFoundException("Resource not found " + CREDENTIALS_FILE_PATH));
        var clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));
        var scopes = List.of(SheetsScopes.SPREADSHEETS);

        var flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                clientSecrets,
                scopes
        )
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
                .authorize("user");
    }


    public static Sheets getSheetsService(String spreadsheetId) throws IOException, GeneralSecurityException {
        var credential = authorize(spreadsheetId);
        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
