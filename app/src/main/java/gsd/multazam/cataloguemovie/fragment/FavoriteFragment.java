package gsd.multazam.cataloguemovie.fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gsd.multazam.cataloguemovie.R;
import gsd.multazam.cataloguemovie.adapter.FavoriteAdapter;
import gsd.multazam.cataloguemovie.model.Favorite;

import static gsd.multazam.cataloguemovie.db.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */

public class FavoriteFragment extends Fragment implements FavoriteAdapter.IFavoAdapter {
    private static final String TAG = "FavoriteFragment";
    private RecyclerView rv;
    private Cursor list;
    private FavoriteAdapter mAdapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rv = getView().findViewById(R.id.recyclerViewFavorite);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new FavoriteAdapter(getContext(), list, FavoriteFragment.this);
        rv.setAdapter(mAdapter);
        new LoadFavMov().execute();
    }

    @Override
    public void doClick(int pos) {

    }

    private Favorite getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Favorite(list);
    }

    private class LoadFavMov extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor favorite) {
            super.onPostExecute(favorite);
            list = favorite;
            mAdapter.setListNotes(list);
            mAdapter.notifyDataSetChanged();

            if (list.getCount() == 0) {
                Log.d(TAG, "onPostExecute: 0");
            }
        }
    }

}
