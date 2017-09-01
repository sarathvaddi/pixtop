package com.example.vaddisa.pixtop;

/**
 * Created by vaddisa on 8/19/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;


public class PictureDetails implements Parcelable {

    private String poster_path;
    private String id_hash;
    private String overview;
    private String release_date;
    private String original_title;
    private String title;
    private String id;
    private String user;


    protected PictureDetails(Parcel in) {
        poster_path = in.readString();
        id_hash = in.readString();
        overview = in.readString();
        release_date = in.readString();
        original_title = in.readString();
        title = in.readString();
        id = in.readString();
        user = in.readString();
    }

    public static final Creator<PictureDetails> CREATOR = new Creator<PictureDetails>() {
        @Override
        public PictureDetails createFromParcel(Parcel in) {
            return new PictureDetails(in);
        }

        @Override
        public PictureDetails[] newArray(int size) {
            return new PictureDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public PictureDetails(String poster_path, String id_hash, String overview,
                          String release_date, String original_title,
                          String title) {
        this.poster_path = poster_path;
        this.id_hash = id_hash;
        this.overview = overview;
        this.release_date = release_date;
        this.original_title = original_title;
        this.title = title;


    }

    public PictureDetails(String id, String user) {
        this.id = id;
        this.user = user;

    }

    public PictureDetails() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeString(id_hash);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(original_title);
        dest.writeString(title);
        dest.writeString(id);
        dest.writeString(user);
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getId_hash() {
        return id_hash;
    }

    public void setId_hash(String id_hash) {
        this.id_hash = id_hash;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

