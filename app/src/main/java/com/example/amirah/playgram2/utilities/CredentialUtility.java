package com.example.amirah.playgram2.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.amirah.playgram2.R;

public class CredentialUtility {

    public static void saveLoginInfo(Context context, String username, String password){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.credential_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.ig_username), username);
        editor.putString(context.getString(R.string.ig_password), password);
        editor.putBoolean("logged_in", true);
        editor.apply();
    }
}
