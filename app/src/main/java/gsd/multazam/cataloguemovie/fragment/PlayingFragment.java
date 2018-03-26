package gsd.multazam.cataloguemovie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import gsd.multazam.cataloguemovie.adapter.MovieAdapter;
import gsd.multazam.cataloguemovie.model.Movie;

public class PlayingFragment extends Fragment implements MovieAdapter.IMovieAdapter {
    private static Bundle mBundleRecyclerViewState;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private ArrayList<Movie> mList = new ArrayList<>();
    private MovieAdapter mAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe;
    private String TAG = "PlayingFragment";
    private Parcelable listState;
    private String SAVED_RECYCLER_VIEW_DATASET_ID = "2";

    public PlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playing, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        swipe = getView().findViewById(R.id.swipeLayoutPlaying);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        recyclerView = getView().findViewById(R.id.recyclerViewPlaying);
        if (mBundleRecyclerViewState != null) {
            Log.d(TAG, "onResume: mBundle not null");
            listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mList = mBundleRecyclerViewState.getParcelableArrayList(SAVED_RECYCLER_VIEW_DATASET_ID);
            mAdapter = new MovieAdapter(getContext(), mList, this);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
            recyclerView.setAdapter(mAdapter);
        } else {
            Log.d(TAG, "onResume: bundle is null");
            swipe.post(new Runnable() {
                @Override
                public void run() {
                    swipe.setRefreshing(true);
                    loadData();
                }
            });
        }
    }

    private void loadData() {
        Log.d(TAG, "loadData: loading");
        swipe.setRefreshing(true);
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
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        mAdapter = new MovieAdapter(getContext(), mList, PlayingFragment.this);
                        recyclerView.setAdapter(mAdapter);
                        swipe.setRefreshing(false);

                        mBundleRecyclerViewState = new Bundle();
                        listState = recyclerView.getLayoutManager().onSaveInstanceState();
                        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
                        mBundleRecyclerViewState.putParcelableArrayList(SAVED_RECYCLER_VIEW_DATASET_ID, mList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
            }
        });
        rq.add(reqData);
    }

    @Override
    public void doClick(int pos) {
        Intent i = new Intent(getActivity(), DetailActivity.class);
        Movie mMovie = new Movie();
        mMovie.setId(mList.get(pos).getId());
        mMovie.setPoster(mList.get(pos).getPoster());
        mMovie.setRelease_date(mList.get(pos).getRelease_date());
        mMovie.setOverview(mList.get(pos).getOverview());
        mMovie.setPopularity(mList.get(pos).getPopularity());
        mMovie.setLanguage(mList.get(pos).getLanguage());
        mMovie.setVoteavg(mList.get(pos).getVoteavg());
        mMovie.setTitle(mList.get(pos).getTitle());
        i.putExtra(DetailActivity.MOVIE, mMovie);
        startActivity(i);
    }
}
