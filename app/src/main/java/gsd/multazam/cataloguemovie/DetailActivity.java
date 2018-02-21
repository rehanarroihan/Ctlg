package gsd.multazam.cataloguemovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    public static final String IMG_EXTRA = "movie_cover";
    public static final String TITLE_EXTRA = "movie_title";
    public static final String DESC_EXTRA = "movie_desc";
    public static final String LANG_EXTRA = "movie_lang";
    public static final String POP_EXTRA = "movie_pop";
    public static final String VOTE_EXTRA = "movie_vote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getIntent().getExtras().getString(TITLE_EXTRA));

        ImageView ivCover = findViewById(R.id.imageViewCover);
        TextView tvDesc = findViewById(R.id.textViewDetailDesc);
        Glide.with(this).load("https://image.tmdb.org/t/p/w185"
                + getIntent().getExtras().getString(IMG_EXTRA)).into(ivCover);
        tvDesc.setText(getIntent().getExtras().getString(DESC_EXTRA));

        //incv
        TextView tvTitle = findViewById(R.id.textViewDetailTitle);
        TextView tvLang = findViewById(R.id.textViewDetailLang);
        TextView tvPop = findViewById(R.id.textViewDetailPop);
        TextView tvVote = findViewById(R.id.textViewDetailVote);
        tvTitle.setText(Html.fromHtml("<b>Title : </b>" + getIntent().getExtras().getString(TITLE_EXTRA)));
        tvLang.setText(Html.fromHtml("<b>Language : </b>" + getIntent().getExtras().getString(LANG_EXTRA)));
        tvPop.setText(Html.fromHtml("<b>Popularity : </b>" + getIntent().getExtras().getString(POP_EXTRA)));
        tvVote.setText(Html.fromHtml("<b>Vote Average : </b>" + getIntent().getExtras().getString(VOTE_EXTRA)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
