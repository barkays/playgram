package com.example.amirah.playgram2.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.amirah.playgram2.entity.Postingan;
import com.example.amirah.playgram2.repository.PostinganRepository;

import java.util.List;

public class PostinganViewModel extends AndroidViewModel {

    private PostinganRepository mPostinganRepository;
    private LiveData<List<Postingan>> mAllPostingans;

    public PostinganViewModel(@NonNull Application application) {
        super(application);
        mPostinganRepository = new PostinganRepository(application);
        mAllPostingans = mPostinganRepository.getmAllPostingans();
    }

    public LiveData<List<Postingan>> getmAllPostingans() {
        return mAllPostingans;
    }

    public void setmAllPostingans(LiveData<List<Postingan>> mAllPostingans) {
        this.mAllPostingans = mAllPostingans;
    }

    public void insert(Postingan postingan){
        mPostinganRepository.insert(postingan);
    }
}
