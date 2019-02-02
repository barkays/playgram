package com.example.amirah.playgram2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amirah.playgram2.utilities.CredentialUtility;
import com.example.amirah.playgram2.utilities.IgUtility;

public class LoginInstagram extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_instagram);


    }

    public void login(View view) {
        this.setupLogin();
    }

    private void setupLogin() {
        final String username = ((EditText)findViewById(R.id.usernameText)).getText().toString();
        final String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();

        new Thread(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        // a potentially time consuming task
                        IgUtility instagram = IgUtility.getObject(username, password);
                        if(instagram.isLoggedIn()){
                            CredentialUtility.saveLoginInfo(LoginInstagram.this, username, password);

                            Intent intent = new Intent(LoginInstagram.this, TabView.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }.start();
    }

}
