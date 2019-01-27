package com.example.amirah.playgram2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.amirah.playgram2.adapter.PostinganListAdapter;
import com.example.amirah.playgram2.entity.Postingan;
import com.example.amirah.playgram2.model.PostinganViewModel;

import java.sql.Timestamp;
import java.util.List;

public class TabView extends AppCompatActivity {

    public static final int SELECTED_FILE_UPLOAD_CODE = 400;
    public static final int READ_EXTERNAL_STORAGE_REQUEST_PERMISSION = 200;
    public static final int NEW_POSTINGAN_ACTIVITY_REQUEST_CODE = 1;
    private PostinganViewModel mPostinganViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view);

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

}
