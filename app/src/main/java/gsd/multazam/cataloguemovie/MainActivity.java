package gsd.multazam.cataloguemovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import gsd.multazam.cataloguemovie.fragment.FavoriteFragment;
import gsd.multazam.cataloguemovie.fragment.PlayingFragment;
import gsd.multazam.cataloguemovie.fragment.UpcomingFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        changePage(R.id.nav_now);
        navigationView.setCheckedItem(R.id.nav_now);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        changePage(id);
        return true;
    }

    private void changePage(int id) {
        Fragment fragment = null;
        if (id == R.id.nav_now) {
            fragment = new PlayingFragment();
            setTitle(getResources().getString(R.string.now_playing));
        } else if (id == R.id.nav_soon) {
            fragment = new UpcomingFragment();
            setTitle(getResources().getString(R.string.coming_soon));
        } else if (id == R.id.nav_search) {
            fragment = new PlayingFragment();
            setTitle(getResources().getString(R.string.now_playing));
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        } else if (id == R.id.nav_setting) {
            fragment = new PlayingFragment();
            setTitle(getResources().getString(R.string.now_playing));
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
        } else if (id == R.id.nav_fav) {
            fragment = new FavoriteFragment();
            setTitle(getResources().getString(R.string.favorite));
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitNow();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
