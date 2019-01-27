package com.example.amirah.playgram2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.amirah.playgram2.utilities.IgUtility;

public class LoginInstagram extends AppCompatActivity {

    private IgUtility instagram = null;


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

        if(instagram == null || !instagram.isLoggedIn()){
            new Thread(){
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // a potentially time consuming task
                            instagram = IgUtility.getObject(username, password);
                            instagram.setUsername(username);
                            instagram.setPassword(password);
                            try {
                                instagram.login();
                                if(instagram.getInstagramClient().getLastResponse().code() != 200){
                                    throw new Exception();
                                }
                                saveLoginInfo(username, password);
                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                            if(instagram.isLoggedIn()){
                                Intent intent = new Intent(LoginInstagram.this, TabView.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }.start();
        }
    }

    private void saveLoginInfo(String username, String password){
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.credential_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.ig_username), username);
        editor.putString(getString(R.string.ig_password), password);
        editor.apply();
    }

}
