package com.example.amirah.playgram2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amirah.playgram2.utilities.CredentialUtility;
import com.example.amirah.playgram2.utilities.IgUtility;

import java.util.Objects;

public class LoginPlaygram extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_playgram);
    }

    public void createAcc(View view) {
        Intent intent = new Intent(LoginPlaygram.this, CreateAccount.class);

        startActivity(intent);
    }

    public void doLoginPg(View view) {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.credential_file_key), Context.MODE_PRIVATE);

        String savedEmail = sharedPref.getString(getString(R.string.pg_email), "");
        String savedPassword = sharedPref.getString(getString(R.string.pg_password), "");
        String savedIgUsername = sharedPref.getString(getString(R.string.ig_username), "");

        String email = ((EditText)findViewById(R.id.etEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.etPassword)).getText().toString();

        if(Objects.equals(savedEmail, email) && Objects.equals(savedPassword, password)){
            Intent intent = new Intent(LoginPlaygram.this, TabView.class);
            intent.putExtra("username", savedIgUsername);
            startActivity(intent);
        }
    }
}
