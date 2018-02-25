package gsd.multazam.cataloguemovie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import gsd.multazam.cataloguemovie.DetailActivity;
import gsd.multazam.cataloguemovie.R;
import gsd.multazam.cataloguemovie.SearchActivity;
import gsd.multazam.cataloguemovie.adapter.MovsAdapter;
import gsd.multazam.cataloguemovie.model.Movie;

public class PlayingFragment extends Fragment implements MovsAdapter.IMovieAdapter {
    ArrayList<Movie> mList = new ArrayList<>();
    MovsAdapter mAdapter;

    public PlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playing, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    private void loadData() {
        mList.clear();
        RequestQueue rq = Volley.newRequestQueue(getContext());
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + SearchActivity.API_KEY + "&language=en-US";
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

                        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewPlaying);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        mAdapter = new MovsAdapter(getContext(), mList, PlayingFragment.this);
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
    public void doClick(int pos) {
        Intent i = new Intent(getActivity(), DetailActivity.class);
        i.putExtra(DetailActivity.HOTEL, mList.get(pos));
        startActivity(i);
    }
}
