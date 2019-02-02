package com.example.amirah.playgram2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.credential_file_key), Context.MODE_PRIVATE);
        final boolean isLoggedIn = sharedPreferences.getBoolean("logged_in", false);
        final boolean firstTimeUse = sharedPreferences.getBoolean("first_time_use", true);
        Thread thread = new Thread(){
            public void run(){
            try{
                sleep(4000);
            } catch (InterruptedException e){
                e.printStackTrace();
            } finally {
                if (isLoggedIn){
                    startActivity(new Intent(SplashActivity.this, TabView.class));
                } else if (!firstTimeUse){
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
            }
        };
        thread.start();

    }
}
