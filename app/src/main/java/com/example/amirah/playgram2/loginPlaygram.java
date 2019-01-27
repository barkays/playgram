package com.example.amirah.playgram2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class loginPlaygram extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_playgram);


    }

    public void createAcc(View view) {
        Intent intent = new Intent(loginPlaygram.this,createAcc.class);

        startActivity(intent);
    }

}
