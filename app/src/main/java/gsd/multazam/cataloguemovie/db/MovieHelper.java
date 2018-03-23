package gsd.multazam.cataloguemovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import gsd.multazam.cataloguemovie.model.Movie;

import static android.provider.BaseColumns._ID;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.FavColumns.LANGUAGE;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.FavColumns.OVERVIEW;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.FavColumns.POPULARITY;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.FavColumns.POSTER;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.FavColumns.RELEASE_DATE;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.FavColumns.TITLE;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.FavColumns.VOTE;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.TABLE_MOVIE;

/**
 * Created by Rehan on 5/03/2018.
 */

public class MovieHelper {
    private static String DATABASE_TABLE = TABLE_MOVIE;
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<Movie> query() {
        ArrayList<Movie> arrayList = new ArrayList<Movie>();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null, _ID + " DESC"
                , null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setVoteavg(cursor.getString(cursor.getColumnIndexOrThrow(VOTE)));
                movie.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE)));
                movie.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(POPULARITY)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));

                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
