package gsd.multazam.favoritemovie.model;

import android.database.Cursor;

import java.io.Serializable;

import gsd.multazam.favoritemovie.db.DatabaseContract;

import static gsd.multazam.favoritemovie.db.DatabaseContract.getColumnInt;
import static gsd.multazam.favoritemovie.db.DatabaseContract.getColumnString;

/**
 * Created by Rehan on 6/03/2018.
 */

public class Favorite implements Serializable {
    private int id;
    private String voteavg;
    private String language;
    private String popularity;
    private String title;
    private String overview;
    private String release_date;
    private String poster;

    public Favorite() {
    }

    public Favorite(Cursor cursor) {
        this.id = getColumnInt(cursor, DatabaseContract.FavColumns._ID);
        this.voteavg = getColumnString(cursor, DatabaseContract.FavColumns.VOTE);
        this.language = getColumnString(cursor, DatabaseContract.FavColumns.LANGUAGE);
        this.popularity = getColumnString(cursor, DatabaseContract.FavColumns.POPULARITY);
        this.title = getColumnString(cursor, DatabaseContract.FavColumns.TITLE);
        this.overview = getColumnString(cursor, DatabaseContract.FavColumns.OVERVIEW);
        this.release_date = getColumnString(cursor, DatabaseContract.FavColumns.RELEASE_DATE);
        this.poster = getColumnString(cursor, DatabaseContract.FavColumns.POSTER);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoteavg() {
        return voteavg;
    }

    public void setVoteavg(String voteavg) {
        this.voteavg = voteavg;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}

