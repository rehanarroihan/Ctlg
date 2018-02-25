package gsd.multazam.cataloguemovie.model;

import java.io.Serializable;

/**
 * Created by rehan on 25/02/18.
 */

public class Movie implements Serializable {
    private int id;
    private String voteavg;
    private String language;
    private String popularity;
    private String title;
    private String overview;
    private String release_date;
    private String poster;

    public Movie() {
    }

    public Movie(int id, String voteavg, String language, String popularity, String title, String overview, String release_date, String poster) {
        this.id = id;
        this.voteavg = voteavg;
        this.language = language;
        this.popularity = popularity;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster = poster;
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
