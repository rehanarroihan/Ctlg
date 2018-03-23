package gsd.multazam.favoritemovie;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import gsd.multazam.favoritemovie.model.Favorite;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    private Favorite favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Uri uri = getIntent().getData();
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) favorite = new Favorite(cursor);
        cursor.close();

        setTitle(favorite.getTitle());

        ImageView ivCover = findViewById(R.id.imageViewCover);
        TextView tvDesc = findViewById(R.id.textViewDetailDesc);
        Glide.with(this).load("https://image.tmdb.org/t/p/w185"
                + favorite.getPoster()).into(ivCover);
        tvDesc.setText(favorite.getOverview());

        //incv
        TextView tvTitle = findViewById(R.id.textViewDetailTitle);
        TextView tvLang = findViewById(R.id.textViewDetailLang);
        TextView tvPop = findViewById(R.id.textViewDetailPop);
        TextView tvVote = findViewById(R.id.textViewDetailVote);
        tvTitle.setText(Html.fromHtml("<b>" + getResources().getString(R.string.title) + " : </b>" + favorite.getTitle()));
        tvLang.setText(Html.fromHtml("<b>" + getResources().getString(R.string.language) + " : </b>" + favorite.getLanguage()));
        tvPop.setText(Html.fromHtml("<b>" + getResources().getString(R.string.popularity) + " : </b>" + favorite.getPopularity()));
        tvVote.setText(Html.fromHtml("<b>" + getResources().getString(R.string.vote) + " : </b>" + favorite.getVoteavg()));
        Log.d(TAG, "onCreate: id: " + favorite.getId());
    }
}
