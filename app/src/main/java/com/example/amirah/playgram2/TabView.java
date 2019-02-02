package com.example.amirah.playgram2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.amirah.playgram2.adapter.PostinganListAdapter;
import com.example.amirah.playgram2.entity.Postingan;
import com.example.amirah.playgram2.model.PostinganViewModel;
import com.example.amirah.playgram2.service.PostingService;

import java.sql.Timestamp;
import java.util.List;

public class TabView extends AppCompatActivity {

    public static final int SELECTED_FILE_UPLOAD_CODE = 400;
    public static final int READ_EXTERNAL_STORAGE_REQUEST_PERMISSION = 200;
    public static final int NEW_POSTINGAN_ACTIVITY_REQUEST_CODE = 1;
    private PostinganViewModel mPostinganViewModel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view);

        sharedPreferences = getSharedPreferences(getString(R.string.credential_file_key), Context.MODE_PRIVATE);

        PostingService mPostingService = new PostingService();
        Intent mServiceIntent = new Intent(getApplicationContext(), mPostingService.getClass());

        if (!isServicesRunning(mPostingService.getClass())) {
            startService(mServiceIntent);
        }

        String loginUsername = sharedPreferences.getString(getString(R.string.ig_username), "");
        TextView usernameText = findViewById(R.id.textUsernameTabView);
        usernameText.setText(loginUsername);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final PostinganListAdapter adapter = new PostinganListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPostinganViewModel = ViewModelProviders.of(this).get(PostinganViewModel.class);

        mPostinganViewModel.getmAllPostingans().observe(this, new Observer<List<Postingan>>() {
            @Override
            public void onChanged(@Nullable List<Postingan> postingans) {
                adapter.setPostingans(postingans);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TabView.NEW_POSTINGAN_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Postingan postingan = new Postingan();
            postingan.setCaption(data.getStringExtra(AddSchedule.CAPTION));
            postingan.setPostTime(data.getStringExtra(AddSchedule.POST_TIME));
            postingan.setPathPicture(data.getStringExtra(AddSchedule.IMAGE_PATH));
            postingan.setId(String.valueOf(new Timestamp(System.currentTimeMillis())));
            postingan.setStatus(true);
            mPostinganViewModel.insert(postingan);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void posting(View view) {
        Intent intent = new Intent(this, AddSchedule.class);
        startActivityForResult(intent, TabView.NEW_POSTINGAN_ACTIVITY_REQUEST_CODE);
    }

    private boolean isServicesRunning(Class<? extends PostingService> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    public void logout(View view) {
        sharedPreferences.edit().clear().apply();
        finish();
    }
}
