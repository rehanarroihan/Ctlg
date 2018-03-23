package gsd.multazam.favoritemovie;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import gsd.multazam.favoritemovie.adapter.FavoriteAdapter;
import gsd.multazam.favoritemovie.model.Favorite;

import static gsd.multazam.favoritemovie.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView rv;
    private FavoriteAdapter mAdapter;
    private Cursor mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.recyclerViewFavo);
        mAdapter = new FavoriteAdapter(this, mList, new FavoriteAdapter.IFavoAdapter() {
            @Override
            public void doClick(int pos) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Uri uri = Uri.parse(CONTENT_URI + "/" + getItem(pos).getId());
                intent.setData(uri);
                startActivity(intent);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
        new LoadFavMov().execute();
    }

    private Favorite getItem(int position) {
        if (!mList.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Favorite(mList);
    }

    private class LoadFavMov extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor favorite) {
            super.onPostExecute(favorite);
            mList = favorite;
            mAdapter.setListNotes(mList);
            mAdapter.notifyDataSetChanged();

            if (mList.getCount() == 0) {
                Log.d(TAG, "onPostExecute: 0");
            }
        }
    }
}
