package com.ming.blueprint.mvp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ming on 2017/9/15 15:04.
 * Desc:
 */

public class Movie implements Parcelable {
    private List<Movie> subjects;
    private Movie images;
    private List<String> genres;
    private String title;
    private String year;
    private String large;

    protected Movie(Parcel in) {
        subjects = in.createTypedArrayList(Movie.CREATOR);
        images = in.readParcelable(Movie.class.getClassLoader());
        genres = in.createStringArrayList();
        title = in.readString();
        year = in.readString();
        large = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(subjects);
        parcel.writeParcelable(images, i);
        parcel.writeStringList(genres);
        parcel.writeString(title);
        parcel.writeString(year);
        parcel.writeString(large);
    }

    public List<Movie> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Movie> subjects) {
        this.subjects = subjects;
    }

    public Movie getImages() {
        return images;
    }

    public void setImages(Movie images) {
        this.images = images;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }
}
