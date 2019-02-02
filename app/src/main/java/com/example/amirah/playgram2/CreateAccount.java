package com.example.amirah.playgram2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amirah.playgram2.utilities.IgUtility;

public class CreateAccount extends Activity {

    private IgUtility instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);
    }

    private void saveAccountInfo(String email, String pgPassword, String igUsername, String igPassword){
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.credential_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.pg_email), email);
        editor.putString(getString(R.string.pg_password), pgPassword);
        editor.putString(getString(R.string.ig_username), igUsername);
        editor.putString(getString(R.string.ig_password), igPassword);
        editor.putBoolean("logged_in", true);
        editor.apply();
    }

    public void signUpPlaygram(View view) {
        final String email = ((EditText)findViewById(R.id.etEmail)).getText().toString();
        final String password = ((EditText)findViewById(R.id.etPassword)).getText().toString();
        final String confirmPassword = ((EditText)findViewById(R.id.etCPassword)).getText().toString();
        final String igUsername = ((EditText)findViewById(R.id.uname)).getText().toString();
        final String igPassword = ((EditText)findViewById(R.id.pass)).getText().toString();

        new Thread(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        // a potentially time consuming task
                        instagram = IgUtility.getObject(igUsername, igPassword);

                        if(!password.equals(confirmPassword)) {
                            Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_SHORT).show();
                        } else if (!instagram.isLoggedIn()){
                            Toast.makeText(getApplicationContext(), "Instagram account false", Toast.LENGTH_SHORT).show();
                        } else {
                            saveAccountInfo(email, password, igUsername, igPassword);
                            Toast.makeText(getApplicationContext(), "Account registered, please login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                finish();
            }
        }.start();
    }
}
