package com.example.amirah.playgram2.utilities;

import java.io.IOException;
import java.io.Serializable;

import dev.niekirk.com.instagram4android.Instagram4Android;

public class IgUtility implements Serializable {

    private String username;
    private String password;
    private Instagram4Android instagramClient;
    private static volatile IgUtility INSTANCE;

    public static IgUtility getObject(String email, String password) {
        if (INSTANCE == null) {
            synchronized (IgUtility.class) {
                if (INSTANCE == null)
                    INSTANCE = new IgUtility(email, password);
            }
        }
        return INSTANCE;
    }

    private IgUtility(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Instagram4Android getInstagramClient(){
        return instagramClient;
    }

    public boolean isLoggedIn() {
        return instagramClient.isLoggedIn();
    }

    public void setup() {
    }

    public void login() throws IOException {
        instagramClient = Instagram4Android.builder().username(username).password(password).build();
        instagramClient.setup();
        instagramClient.login();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
