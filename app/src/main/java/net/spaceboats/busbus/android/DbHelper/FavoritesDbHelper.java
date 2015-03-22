package net.spaceboats.busbus.android.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zralston on 3/15/15.
 */
public class FavoritesDbHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favorites.db";

    private static final String TYPE_TEXT = " TEXT";
    private static final String SEP_COMMA = ", ";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String SQL_CREATE_STOP =
            " CREATE TABLE IF NOT EXISTS " + FavoritesContract.Stop.TABLE_NAME + " ( "
            + FavoritesContract.Stop.COLUMN_ID + TYPE_TEXT + PRIMARY_KEY + SEP_COMMA
            + FavoritesContract.Stop.COLUMN_NAME + TYPE_TEXT + SEP_COMMA
            + FavoritesContract.Stop.COLUMN_LATITUDE + TYPE_TEXT + SEP_COMMA
            + FavoritesContract.Stop.COLUMN_LONGITUDE + TYPE_TEXT + SEP_COMMA
            + FavoritesContract.Stop.COLUMN_DESCRIPTION + TYPE_TEXT + " )";

    private static final String SQL_DELETE_STOP =
            " DROP TABLE IF EXISTS " + FavoritesContract.Stop.TABLE_NAME;

    private static final String SQL_CREATE_ROUTE =
            " CREATE TABLE IF NOT EXISTS " + FavoritesContract.Route.TABLE_NAME + " ( "
                    + FavoritesContract.Route.COLUMN_ID + TYPE_TEXT + PRIMARY_KEY + SEP_COMMA
                    + FavoritesContract.Route.COLUMN_NAME + TYPE_TEXT + SEP_COMMA
                    + FavoritesContract.Route.COLUMN_SHORT_NAME + TYPE_TEXT + SEP_COMMA
                    + FavoritesContract.Route.COLUMN_COLOR + TYPE_TEXT + " )";

    private static final String SQL_DELETE_ROUTE =
            " DROP TABLE IF EXISTS " + FavoritesContract.Route.TABLE_NAME;

    private static final String SQL_CREATE_ARRIVAL =
            " CREATE TABLE IF NOT EXISTS " + FavoritesContract.Arrival.TABLE_NAME + " ( "
            + FavoritesContract.Arrival.COLUMN_ROUTE_ID + TYPE_TEXT + SEP_COMMA
            + FavoritesContract.Arrival.COLUMN_STOP_ID + TYPE_TEXT + SEP_COMMA
            + "FOREIGN KEY(" + FavoritesContract.Arrival.COLUMN_ROUTE_ID
                    + ") REFERENCES " + FavoritesContract.Route.TABLE_NAME + "("
                    + FavoritesContract.Route.COLUMN_ID + ")" + SEP_COMMA
            + "FOREIGN KEY(" + FavoritesContract.Arrival.COLUMN_STOP_ID
                    + ") REFERENCES " + FavoritesContract.Stop.TABLE_NAME + "("
                    + FavoritesContract.Stop.COLUMN_ID + ")" + " )";

    private static final String SQL_DELETE_ARRIVAL =
            " DROP TABLE IF EXISTS " + FavoritesContract.Arrival.TABLE_NAME;


    public FavoritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_STOP);
        db.execSQL(SQL_CREATE_ROUTE);
        db.execSQL(SQL_CREATE_ARRIVAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ARRIVAL);
        db.execSQL(SQL_DELETE_STOP);
        db.execSQL(SQL_DELETE_ROUTE);
        onCreate(db);
    }
}
