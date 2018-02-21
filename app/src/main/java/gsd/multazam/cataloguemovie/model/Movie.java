package gsd.multazam.cataloguemovie.model;

import org.json.JSONObject;

/**
 * Created by rehan on 21/02/18.
 */

public class Movie {
    private int id;
    private String voteavg;
    private String language;
    private String popularity;
    private String title;
    private String overview;
    private String release_date;
    private String poster;

    public Movie(JSONObject object) {
        try {
            int id = object.getInt("id");
            String voteavg = object.getString("vote_average");
            String language = object.getString("original_language");
            String popularity = object.getString("popularity");
            String poster = object.getString("poster_path");
            String title = object.getString("title");
            String overview = object.getString("overview");
            String release_date = object.getString("release_date");

            this.id = id;
            this.voteavg = voteavg;
            this.language = language;
            this.popularity = popularity;
            this.poster = poster;
            this.title = title;
            this.overview = overview;
            this.release_date = release_date;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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
}
