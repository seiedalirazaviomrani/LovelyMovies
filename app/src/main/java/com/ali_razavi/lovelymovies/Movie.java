package com.ali_razavi.lovelymovies;

import android.os.Parcel;
import android.os.Parcelable;

import static java.lang.Math.round;

/**
 * Created by seiedalirazaviomrani on 11/5/16.
 */

public class Movie implements Parcelable {
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
    private String mOriginalTitle;
    private double mPopularity;
    private double mVoteAverage;
    private String mPosterImageUrl;
    private String mReleaseDate;
    private String mOverview;
    private String mOriginalLanguage;

    public Movie(String title, double popularity, double voteAverage, String posterImageUrl, String releaseDate, String overview, String originalLanguage) {
        this.mOriginalTitle = title;
        this.mPopularity = popularity;
        this.mVoteAverage = voteAverage;
        this.mPosterImageUrl = posterImageUrl;
        this.mReleaseDate = releaseDate;
        this.mOverview = overview;
        this.mOriginalLanguage = originalLanguage;
    }

    protected Movie(Parcel in) {
        mOriginalTitle = in.readString();
        mPopularity = in.readDouble();
        mVoteAverage = in.readDouble();
        mPosterImageUrl = in.readString();
        mReleaseDate = in.readString();
        mOverview = in.readString();
        mOriginalLanguage = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mOriginalTitle);
        dest.writeDouble(mPopularity);
        dest.writeDouble(mVoteAverage);
        dest.writeString(mPosterImageUrl);
        dest.writeString(mReleaseDate);
        dest.writeString(mOverview);
        dest.writeString(mOriginalLanguage);
    }

    String getOriginalTitle() {
        if (mOriginalTitle.isEmpty()) {
            return "isEmpty";
        } else {
            return mOriginalTitle;
        }
    }

    double getPopularity() {
        return round(mPopularity);
    }

    double getVoteAverage() {
        return mVoteAverage;
    }

    String getPosterImageUrl() {
        if (mPosterImageUrl.isEmpty()) {
            return "isEmpty";
        } else {
            return mPosterImageUrl;
        }
    }

    String getReleaseDate() {
        if (mReleaseDate.isEmpty()) {
            return "isEmpty";
        } else {
            return mReleaseDate;
        }
    }

    String getOverview() {
        if (mOverview.isEmpty()) {
            return "isEmpty";
        } else {
            return mOverview;
        }
    }

    String getOriginalLanguage() {
        if (mOriginalLanguage.isEmpty()) {
            return "isEmpty";
        } else {
            return mOriginalLanguage;
        }
    }

}
