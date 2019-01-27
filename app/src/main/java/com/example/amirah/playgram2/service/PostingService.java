package com.example.amirah.playgram2.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.amirah.playgram2.R;
import com.example.amirah.playgram2.entity.Postingan;
import com.example.amirah.playgram2.repository.PostinganRepository;
import com.example.amirah.playgram2.utilities.IgUtility;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dev.niekirk.com.instagram4android.requests.InstagramUploadPhotoRequest;

public class PostingService extends Service {
    private PostinganRepository mPostinganRepository;
    private Timer timer;
    private TimerTask timerTask;
    private IgUtility instagram;
    private SharedPreferences settings;

    @Override
    public void onCreate() {
        settings = this.getSharedPreferences(getString(R.string.credential_file_key), Context.MODE_PRIVATE);
        mPostinganRepository = new PostinganRepository(getApplication());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        startTimer();

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    private void initializeTimerTask(){
        timerTask = new TimerTask() {
            public void run() {
                Log.i("posting service", "running");
                if(instagram == null || !instagram.isLoggedIn()){
                    try {
                        initializeAccount();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                List<Postingan> postingans = mPostinganRepository.getmPostingByTime(new Date(), true);
                for (Postingan postingan: postingans) {
                    if(instagram != null && instagram.isLoggedIn()) {
                        try {
                            instagram.getInstagramClient().sendRequest(new InstagramUploadPhotoRequest(
                                    new File(postingan.getPathPicture()), postingan.getCaption()));
                            postingan.setStatus(false);
                            mPostinganRepository.updateOne(postingan);
                            Log.i("posting", String.format("Post id %s posted", postingan.getId()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
    }

    private void initializeAccount() throws IOException {
        String ig_username = settings.getString(getString(R.string.ig_username), "");
        String ig_password = settings.getString(getString(R.string.ig_password), "");
        instagram = IgUtility.getObject(ig_username, ig_password);
        instagram.setUsername(ig_username);
        instagram.setPassword(ig_password);
        instagram.login();
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 60 seconds
        timer.schedule(timerTask, 1000, 60000); //
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}
