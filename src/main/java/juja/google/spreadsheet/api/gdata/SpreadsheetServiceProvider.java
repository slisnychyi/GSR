package juja.google.spreadsheet.api.gdata;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import static java.util.Arrays.asList;

public class SpreadsheetServiceProvider implements Provider<SpreadsheetService> {

    private static final Logger logger = LogManager.getLogger(SpreadsheetServiceProvider.class.getName());
    private static final String SERVICE_NAME = "juja_table"; //service name could be any

    @Inject
    @Named("serviceAccountId")
    String serviceAccountId;
    @Inject
    @Named("google.spreadsheet.scopes")
    private String scopes;
    @Inject
    @Named("p12.file.path")
    private String p12FilePath;

    public SpreadsheetServiceProvider() {
    }

    public GoogleCredential generateCredentials(){
        GoogleCredential credential = null;
        try {
            //File p12 = new File(this.getClass().getResource("/" + p12FilePath).toURI());
            File p12 = new File(p12FilePath);
            credential = new GoogleCredential.Builder()
                    .setTransport(new NetHttpTransport())
                    .setJsonFactory(new JacksonFactory())
                    .setServiceAccountId(serviceAccountId)
                    .setServiceAccountScopes(asList(scopes.split(";")))
                    .setServiceAccountPrivateKeyFromP12File(p12)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            String message = "Cannot generate google credentials for connection.";
            logger.error(message, e);
        }
        return credential;
    }

    @Override
    public SpreadsheetService get() {
        GoogleCredential credentials = generateCredentials();
        SpreadsheetService service = new SpreadsheetService(SERVICE_NAME);
        service.setOAuth2Credentials(credentials);
        return service;
    }
}
