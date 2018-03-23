package gsd.multazam.cataloguemovie.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Rehan on 5/03/2018.
 */

public class DatabaseContract {
    public static final String AUTHORITY = "gsd.multazam.cataloguemovie";
    public static String TABLE_MOVIE = "movie";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static final class FavColumns implements BaseColumns {
        public static String VOTE = "voteavg";
        public static String LANGUAGE = "language";
        public static String POPULARITY = "popularity";
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String RELEASE_DATE = "release_date";
        public static String POSTER = "poster";
    }
}
