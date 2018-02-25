package gsd.multazam.cataloguemovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import gsd.multazam.cataloguemovie.model.Movie;

public class DetailActivity extends AppCompatActivity {
    public static final String HOTEL = "hotel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_det);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Movie mov = (Movie) getIntent().getSerializableExtra(HOTEL);
        setTitle(mov.getTitle());

        ImageView ivCover = findViewById(R.id.imageViewCover);
        TextView tvDesc = findViewById(R.id.textViewDetailDesc);
        Glide.with(this).load("https://image.tmdb.org/t/p/w185"
                + mov.getPoster()).into(ivCover);
        tvDesc.setText(mov.getOverview());

        //incv
        TextView tvTitle = findViewById(R.id.textViewDetailTitle);
        TextView tvLang = findViewById(R.id.textViewDetailLang);
        TextView tvPop = findViewById(R.id.textViewDetailPop);
        TextView tvVote = findViewById(R.id.textViewDetailVote);
        tvTitle.setText(Html.fromHtml("<b>" + getResources().getString(R.string.title) + " : </b>" + mov.getTitle()));
        tvLang.setText(Html.fromHtml("<b>" + getResources().getString(R.string.language) + " : </b>" + mov.getLanguage()));
        tvPop.setText(Html.fromHtml("<b>" + getResources().getString(R.string.popularity) + " : </b>" + mov.getPopularity()));
        tvVote.setText(Html.fromHtml("<b>" + getResources().getString(R.string.vote) + " : </b>" + mov.getVoteavg()));
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
