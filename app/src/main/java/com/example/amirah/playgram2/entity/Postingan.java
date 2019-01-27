package com.example.amirah.playgram2.entity;

import android.annotation.SuppressLint;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Postingan {
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "path_picture")
    @NonNull
    private String pathPicture;

    @ColumnInfo(name = "caption")
    private String caption;

    @ColumnInfo(name = "tag_people")
    private String tagPeople;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "post_time")
    @NonNull
    private Date postTime;

    @ColumnInfo(name = "delete_time")
    private Date deleteTime;

    @NonNull
    @ColumnInfo(name = "status")
    private boolean status;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getPathPicture() {
        return pathPicture;
    }

    public void setPathPicture(@NonNull String pathPicture) {
        this.pathPicture = pathPicture;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getTagPeople() {
        return tagPeople;
    }

    public void setTagPeople(String tagPeople) {
        this.tagPeople = tagPeople;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NonNull
    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(@NonNull Date postTime) {
        this.postTime = postTime;
    }

    public void setPostTime(@NonNull String postTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.postTime = format.parse(postTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getPostDate(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        return format.format(postTime);
    }

    public String getPostDateTime(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(postTime);
    }

    public Date getDeleteTime() {
        return deleteTime;
    }
    public void setDeleteTime(Date deleteTime){
        this.deleteTime = deleteTime;
    }

    public void setDeleteTime( String deleteTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.deleteTime = format.parse(deleteTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public boolean isStatus() {
        return status;
    }

    public void setStatus(@NonNull boolean status) {
        this.status = status;
    }
}
