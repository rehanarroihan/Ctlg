package gsd.multazam.cataloguemovie;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import gsd.multazam.cataloguemovie.adapter.MovieAdapter;
import gsd.multazam.cataloguemovie.model.Movie;
import gsd.multazam.cataloguemovie.util.LoadMovie;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    public static final String API_KEY = "d4bee1442fda04e0b421566f1a54e4ae";
    static final String EXTRAS_FILM = "EXTRAS_FILM";
    private static final String TAG = "SearchActivity";
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
        setTitle("Search Movie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                getLoaderManager().restartLoader(0, bundle, SearchActivity.this);
            }
        });

        String film = etMovie.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_FILM, film);
        getLoaderManager().initLoader(0, bundle, this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent i = new Intent(SearchActivity.this, DetailActivity.class);
                i.putExtra(DetailActivity.HOTEL, mList.get(pos));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();
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
}
