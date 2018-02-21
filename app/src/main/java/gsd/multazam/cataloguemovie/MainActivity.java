package gsd.multazam.cataloguemovie;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import gsd.multazam.cataloguemovie.adapter.MovieAdapter;
import gsd.multazam.cataloguemovie.model.Movie;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    static final String EXTRAS_FILM = "EXTRAS_FILM";
    private static final String TAG = "MainActivity";
    ListView lv;
    ProgressBar pb;
    MovieAdapter mAdapter;
    EditText etMovie;
    Button btCari;
    ArrayList<Movie> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Movie Catalogue");

        mAdapter = new MovieAdapter(this);
        mAdapter.notifyDataSetChanged();

        pb = findViewById(R.id.progressBar);
        lv = findViewById(R.id.listView);
        lv.setAdapter(mAdapter);
        etMovie = findViewById(R.id.editTextSearch);
        btCari = findViewById(R.id.buttonSearch);
        btCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String film = etMovie.getText().toString();
                if (TextUtils.isEmpty(film)) return;
                Bundle bundle = new Bundle();
                bundle.putString(EXTRAS_FILM, film);
                lv.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
                getLoaderManager().restartLoader(0, bundle, MainActivity.this);
            }
        });

        String film = etMovie.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_FILM, film);
        getLoaderManager().initLoader(0, bundle, this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra(DetailActivity.TITLE_EXTRA, mList.get(pos).getTitle());
                i.putExtra(DetailActivity.IMG_EXTRA, mList.get(pos).getPoster());
                i.putExtra(DetailActivity.DESC_EXTRA, mList.get(pos).getOverview());
                i.putExtra(DetailActivity.POP_EXTRA, mList.get(pos).getPopularity());
                i.putExtra(DetailActivity.VOTE_EXTRA, mList.get(pos).getVoteavg());
                i.putExtra(DetailActivity.LANG_EXTRA, mList.get(pos).getLanguage());
                startActivity(i);
            }
        });
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        String query = "";
        if (args != null) {
            query = args.getString(EXTRAS_FILM);
        }
        return new LoadMovie(this, query);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        lv.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
        mList = data;
        mAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        lv.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        mAdapter.setData(null);
    }
}
