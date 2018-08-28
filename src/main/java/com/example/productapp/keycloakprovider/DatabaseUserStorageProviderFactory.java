package com.example;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;
import org.keycloak.storage.UserStorageProviderModel;

import com.example.productapp.keycloakprovider.DatabaseUserStorageProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;;

public class DatabaseUserStorageProviderFactory implements UserStorageProviderFactory<DatabaseUserStorageProvider> {

    private static final Logger logger = Logger.getLogger(DatabaseUserStorageProviderFactory.class);

    public static final String PROVIDER_NAME = "readonly-property-file";

    protected Properties properties = new Properties();

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }


    @Override
    public void init(Config.Scope config) {
        InputStream is = getClass().getClassLoader().getResourceAsStream("/users.properties");

        if (is == null) {
            logger.warn("Could not find users.properties in classpath");
        } else {
            try {
                properties.load(is);
            } catch (IOException ex) {
                logger.error("Failed to load users.properties file", ex);
            }
        }
    }

    @Override
    public DatabaseUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        return new DatabaseUserStorageProvider(session, model, properties);
    }

}
