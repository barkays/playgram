package com.example.amirah.playgram2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginIg(View view) {
        Intent intent = new Intent(LoginActivity.this,LoginInstagram.class);

        startActivity(intent);
    }

    public void loginPg(View view) {
        Intent intent = new Intent(LoginActivity.this,LoginPlaygram.class);

        startActivity(intent);
    }
}
