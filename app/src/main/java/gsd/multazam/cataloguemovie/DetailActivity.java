package gsd.multazam.cataloguemovie;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import gsd.multazam.cataloguemovie.db.DatabaseContract;
import gsd.multazam.cataloguemovie.model.Movie;

import static android.provider.BaseColumns._ID;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.CONTENT_URI;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.FavColumns.LANGUAGE;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.FavColumns.OVERVIEW;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.FavColumns.POPULARITY;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.FavColumns.POSTER;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.FavColumns.RELEASE_DATE;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.FavColumns.TITLE;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.FavColumns.VOTE;

public class DetailActivity extends AppCompatActivity {
    public static final String MOVIE = "movie";
    private static final String TAG = "DetailActivity";
    private boolean isFav = false;
    private Movie mov;
    private ImageView ivFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_det);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mov = getIntent().getParcelableExtra(MOVIE);
        setTitle(mov.getTitle());
        Log.d(TAG, "onCreate: id film : " + mov.getId());

        ImageView ivCover = findViewById(R.id.imageViewCover);
        TextView tvDesc = findViewById(R.id.textViewDetailDesc);
        Glide.with(this).load("https://image.tmdb.org/t/p/w185"
                + mov.getPoster()).into(ivCover);
        tvDesc.setText(mov.getOverview());
        ivFavorite = findViewById(R.id.imageViewFavorite);
        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFav) {
                    ContentValues values = new ContentValues();
                    values.put(_ID, mov.getId());
                    values.put(VOTE, mov.getVoteavg());
                    values.put(LANGUAGE, mov.getLanguage());
                    values.put(POPULARITY, mov.getPopularity());
                    values.put(TITLE, mov.getTitle());
                    values.put(OVERVIEW, mov.getOverview());
                    values.put(RELEASE_DATE, mov.getRelease_date());
                    values.put(POSTER, mov.getPoster());
                    getContentResolver().insert(CONTENT_URI, values);
                } else {
                    getContentResolver().delete(
                            Uri.parse(DatabaseContract.CONTENT_URI + "/" + mov.getId()),
                            null,
                            null
                    );
                    Toast.makeText(DetailActivity.this, getResources().getString(R.string.delFav), Toast.LENGTH_SHORT).show();
                }
                checkFav();
            }
        });

        //incv
        TextView tvTitle = findViewById(R.id.textViewDetailTitle);
        TextView tvLang = findViewById(R.id.textViewDetailLang);
        TextView tvPop = findViewById(R.id.textViewDetailPop);
        TextView tvVote = findViewById(R.id.textViewDetailVote);
        tvTitle.setText(Html.fromHtml("<b>" + getResources().getString(R.string.title) + " : </b>" + mov.getTitle()));
        tvLang.setText(Html.fromHtml("<b>" + getResources().getString(R.string.language) + " : </b>" + mov.getLanguage()));
        tvPop.setText(Html.fromHtml("<b>" + getResources().getString(R.string.popularity) + " : </b>" + mov.getPopularity()));
        tvVote.setText(Html.fromHtml("<b>" + getResources().getString(R.string.vote) + " : </b>" + mov.getVoteavg()));

        checkFav();
    }

    private void checkFav() {
        Cursor cursor = getContentResolver().query(
                Uri.parse(DatabaseContract.CONTENT_URI + "/" + mov.getId()),
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                isFav = true;
                Log.d(TAG, "checkFav: favo");
            } else {
                isFav = false;
                Log.d(TAG, "checkFav: not favo");
            }
            cursor.close();
        }
        setIcon();
    }

    private void setIcon() {
        if (isFav) {
            ivFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
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
}
