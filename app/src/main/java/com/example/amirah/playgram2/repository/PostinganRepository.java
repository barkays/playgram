package com.example.amirah.playgram2.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.amirah.playgram2.dao.PostinganDao;
import com.example.amirah.playgram2.database.AppDatabase;
import com.example.amirah.playgram2.entity.Postingan;

import java.util.Date;
import java.util.List;

public class PostinganRepository {

    private PostinganDao mPostinganDao;
    private LiveData<List<Postingan>> mAllPostingans;

    public PostinganRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mPostinganDao = db.postinganDao();
        mAllPostingans = mPostinganDao.getAll();
    }

    public LiveData<List<Postingan>> getmAllPostingans() {
        return mAllPostingans;
    }

    public void insert(Postingan postingan){
        new insertAsyncTask(mPostinganDao).execute(postingan);
    }

    public List<Postingan> getmPostingByTime(Date date, boolean status) {
        return mPostinganDao.getByDate(date, status);
    }

    public void updateOne(Postingan postingan){
        mPostinganDao.update(postingan);
    }

    private static class insertAsyncTask extends AsyncTask<Postingan, Void, Void> {

        private PostinganDao mAsyncTaskDao;

        insertAsyncTask(PostinganDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Postingan... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
