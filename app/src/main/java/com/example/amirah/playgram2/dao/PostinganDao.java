package com.example.amirah.playgram2.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.amirah.playgram2.entity.Postingan;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface PostinganDao {
    @Query("SELECT * FROM postingan")
    LiveData<List<Postingan>> getAll();

    @Query("SELECT * FROM postingan WHERE id IN (:id)")
    LiveData<List<Postingan>> loadAllByIds(int[] id);

    @Query("SELECT * FROM postingan LIMIT (:limit), (:offset)")
    LiveData<List<Postingan>> getAll(String limit, String offset);

    @Query("SELECT * FROM postingan WHERE post_time < :date AND status == :status")
    List<Postingan> getByDate(Date date, boolean status);

    @Update(onConflict = REPLACE)
    void update(Postingan postingan);

    @Insert
    void insert(Postingan postingan);

    @Insert
    void insertAll(Postingan... postingans);

    @Delete
    void delete(Postingan postingan);
}
