package gsd.multazam.cataloguemovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static gsd.multazam.cataloguemovie.db.DatabaseContract.TABLE_MOVIE;

/**
 * Created by Rehan on 5/03/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_MOVIE,
            DatabaseContract.FavColumns._ID,
            DatabaseContract.FavColumns.VOTE,
            DatabaseContract.FavColumns.LANGUAGE,
            DatabaseContract.FavColumns.POPULARITY,
            DatabaseContract.FavColumns.TITLE,
            DatabaseContract.FavColumns.OVERVIEW,
            DatabaseContract.FavColumns.RELEASE_DATE,
            DatabaseContract.FavColumns.POSTER
    );
    public static String DATABASE_NAME = "dbmovie";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }
}

