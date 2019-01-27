package com.example.amirah.playgram2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.amirah.playgram2.service.PostingService;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(){
            public void run(){
            try{
                sleep(4000);
            } catch (InterruptedException e){
                e.printStackTrace();
            } finally {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
            }
        };
        thread.start();

    }
}
