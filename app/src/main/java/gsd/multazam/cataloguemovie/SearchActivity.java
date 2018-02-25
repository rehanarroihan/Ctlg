package gsd.multazam.cataloguemovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import gsd.multazam.cataloguemovie.adapter.MovieAdapter;
import gsd.multazam.cataloguemovie.model.Movie;

public class SearchActivity extends AppCompatActivity implements MovieAdapter.IMovieAdapter {
    public static final String API_KEY = "d4bee1442fda04e0b421566f1a54e4ae";
    RecyclerView rv;
    ProgressBar pb;
    MovieAdapter mAdapter;
    TextView tvMsg;
    ArrayList<Movie> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle(getResources().getString(R.string.search_movie));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pb = findViewById(R.id.progressBar);
        rv = findViewById(R.id.recyclerViewSearch);
        tvMsg = findViewById(R.id.textViewMsg);
    }

    private void searchMovie(String query) {
        rv.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        tvMsg.setVisibility(View.GONE);
        mList.clear();
        RequestQueue rq = Volley.newRequestQueue(this);
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + SearchActivity.API_KEY
                + "&language=en-US&query=" + query;
        JsonObjectRequest reqData = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley", "Response : " + response);
                        try {
                            JSONArray result = response.getJSONArray("results");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject data = result.getJSONObject(i);
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        rv.setVisibility(View.VISIBLE);
                        tvMsg.setVisibility(View.GONE);
                        pb.setVisibility(View.GONE);
                        RecyclerView recyclerView = findViewById(R.id.recyclerViewSearch);
                        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                        mAdapter = new MovieAdapter(SearchActivity.this, mList, SearchActivity.this);
                        recyclerView.setAdapter(mAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(reqData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                rv.setVisibility(View.GONE);
                tvMsg.setVisibility(View.VISIBLE);
                pb.setVisibility(View.VISIBLE);
                searchMovie(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void doClick(int pos) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra(DetailActivity.HOTEL, mList.get(pos));
        startActivity(i);
    }
}
