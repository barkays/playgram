package com.example.amirah.playgram2;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Toast;

import com.example.amirah.playgram2.utilities.FileUtility;
import com.example.amirah.playgram2.utilities.IgUtility;

import java.io.File;
import java.io.IOException;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramUploadPhotoRequest;

public class ScheduleActivity extends Activity {

    private static final int SELECTED_FILE_UPLOAD_CODE = 400;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_PERMISSION = 200;
    private Instagram4Android instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.credential_file_key), Context.MODE_PRIVATE);
        String username = sharedPref.getString(getString(R.string.ig_username), null);
        String password = sharedPref.getString(getString(R.string.ig_password), null);

        try{
            instagram = Instagram4Android.builder().username(username).password(password).build();
            instagram.setup();
            instagram.login();
        }catch (Exception e){
            Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();
            this.finish();
        }
        if(!instagram.isLoggedIn()){
            Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();
            this.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SELECTED_FILE_UPLOAD_CODE && resultCode==RESULT_OK) {
            Uri selectedFileUri = data.getData(); //The uri with the location of the file
            String filePath = FileUtility.getPathFromUri(this, selectedFileUri);
            try {
                instagram.sendRequest(new InstagramUploadPhotoRequest(
                        new File(filePath),
                        "Posted with Instagram4j, how cool is that?"));
                Toast.makeText(this, "Image Posted!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFileDialog();
            } else {
                Toast.makeText(this, "Cannot open file, request not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void postinging(View view) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_PERMISSION);

        } else {
            openFileDialog();
        }
    }

    public void openFileDialog(){
        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select a file"), SELECTED_FILE_UPLOAD_CODE);
    }

}
