package gsd.multazam.cataloguemovie.util;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import gsd.multazam.cataloguemovie.SearchActivity;
import gsd.multazam.cataloguemovie.model.Movie;

/**
 * Created by rehan on 21/02/18.
 */

public class LoadMovie extends AsyncTaskLoader<ArrayList<Movie>> {
    private ArrayList<Movie> mList;
    private boolean result = false;
    private String search;

    public LoadMovie(final Context context, String query) {
        super(context);
        onContentChanged();
        this.search = query;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (result)
            deliverResult(mList);
    }

    @Override
    public void deliverResult(final ArrayList<Movie> data) {
        mList = data;
        result = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (result) {
            onReleaseResources(mList);
            mList = null;
            result = false;
        }
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        mList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + SearchActivity.API_KEY
                + "&language=en-US&query=" + search;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");
                    Log.d("Response", "onSuccess: " + result.toString());
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject data = results.getJSONObject(i);
                        Movie mo = new Movie();
                        mo.setId(data.getInt("id"));
                        mo.setVoteavg(data.getString("vote_average"));
                        mo.setLanguage(data.getString("original_language"));
                        mo.setPopularity(data.getString("popularity"));
                        mo.setTitle(data.getString("title"));
                        mo.setOverview(data.getString("overview"));
                        mo.setRelease_date(data.getString("release_date"));
                        mo.setPoster(data.getString("poster_path"));
                        mList.add(mo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
        return mList;
    }

    protected void onReleaseResources(ArrayList<Movie> data) {
    }

}
