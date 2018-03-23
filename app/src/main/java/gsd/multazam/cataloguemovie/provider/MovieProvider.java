package gsd.multazam.cataloguemovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import gsd.multazam.cataloguemovie.db.DatabaseContract;
import gsd.multazam.cataloguemovie.db.MovieHelper;

import static gsd.multazam.cataloguemovie.db.DatabaseContract.AUTHORITY;
import static gsd.multazam.cataloguemovie.db.DatabaseContract.CONTENT_URI;

/**
 * Created by Rehan on 5/03/2018.
 */

public class MovieProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://gsd.multazam.cataloguemovie/movie
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE, MOVIE);

        // content://com.dicoding.mynotesapp/movie/id
        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_MOVIE + "/#",
                MOVIE_ID);
    }

    private MovieHelper mHelper;

    @Override
    public boolean onCreate() {
        mHelper = new MovieHelper(getContext());
        mHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = mHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = mHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = mHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            Toast.makeText(getContext(), "Berhasil menyimpan data", Toast.LENGTH_LONG).show();
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                updated = mHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }
        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = mHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            Toast.makeText(getContext(), "Berhasil menghapus data", Toast.LENGTH_LONG).show();
        }
        return deleted;
    }
}
