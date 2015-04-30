package net.spaceboats.busbus.android.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zralston on 3/15/15.
 */
class FavoritesDbHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "favorites.db";

    private static final String TYPE_TEXT = " TEXT ";
    private static final String SEP_COMMA = ", ";
    private static final String PRIMARY_KEY = " PRIMARY KEY ";
    private static final String FOREIGN_KEY = " FOREIGN KEY ";
    private static final String REFERENCES = " REFERENCES ";
    private static final String OPEN_PAREN = " ( ";
    private static final String CLOSE_PAREN = " ) ";
    private static final String CREATE_TABLE = " CREATE TABLE IF NOT EXISTS ";
    private static final String DROP_TABLE = " DROP TABLE IF EXISTS ";

    private static final String SQL_CREATE_STOP =
            CREATE_TABLE + FavoritesContract.Stop.TABLE_NAME + OPEN_PAREN
            + FavoritesContract.Stop.COLUMN_ID + TYPE_TEXT + PRIMARY_KEY + SEP_COMMA
            + FavoritesContract.Stop.COLUMN_NAME + TYPE_TEXT + SEP_COMMA
            + FavoritesContract.Stop.COLUMN_LATITUDE + TYPE_TEXT + SEP_COMMA
            + FavoritesContract.Stop.COLUMN_LONGITUDE + TYPE_TEXT + SEP_COMMA
            + FavoritesContract.Stop.COLUMN_DESCRIPTION + TYPE_TEXT + CLOSE_PAREN;

    private static final String SQL_DELETE_STOP =
            DROP_TABLE + FavoritesContract.Stop.TABLE_NAME;

    private static final String SQL_CREATE_ROUTE =
            CREATE_TABLE + FavoritesContract.Route.TABLE_NAME + OPEN_PAREN
                    + FavoritesContract.Route.COLUMN_ID + TYPE_TEXT + PRIMARY_KEY + SEP_COMMA
                    + FavoritesContract.Route.COLUMN_NAME + TYPE_TEXT + SEP_COMMA
                    + FavoritesContract.Route.COLUMN_SHORT_NAME + TYPE_TEXT + SEP_COMMA
                    + FavoritesContract.Route.COLUMN_COLOR + TYPE_TEXT + CLOSE_PAREN;

    private static final String SQL_DELETE_ROUTE =
            DROP_TABLE + FavoritesContract.Route.TABLE_NAME;

    private static final String SQL_CREATE_ARRIVAL =
            CREATE_TABLE + FavoritesContract.Arrival.TABLE_NAME + OPEN_PAREN
            + FavoritesContract.Arrival.COLUMN_ROUTE_ID + TYPE_TEXT + SEP_COMMA
            + FavoritesContract.Arrival.COLUMN_STOP_ID + TYPE_TEXT + SEP_COMMA
            + FOREIGN_KEY + OPEN_PAREN + FavoritesContract.Arrival.COLUMN_ROUTE_ID
                    + CLOSE_PAREN + REFERENCES + FavoritesContract.Route.TABLE_NAME + OPEN_PAREN
                    + FavoritesContract.Route.COLUMN_ID + CLOSE_PAREN + SEP_COMMA
            + FOREIGN_KEY + OPEN_PAREN + FavoritesContract.Arrival.COLUMN_STOP_ID
                    + CLOSE_PAREN + REFERENCES + FavoritesContract.Stop.TABLE_NAME + OPEN_PAREN
                    + FavoritesContract.Stop.COLUMN_ID + CLOSE_PAREN + CLOSE_PAREN;

    private static final String SQL_DELETE_ARRIVAL =
            DROP_TABLE + FavoritesContract.Arrival.TABLE_NAME;

    private static final String SQL_CREATE_PROVIDER =
            CREATE_TABLE + FavoritesContract.Provider.TABLE_NAME + OPEN_PAREN
            + FavoritesContract.Provider.COLUMN_ID + TYPE_TEXT + SEP_COMMA
            + FavoritesContract.Provider.COLUMN_CREDIT + TYPE_TEXT + CLOSE_PAREN;

    private static final String SQL_DELETE_PROVIDER =
            DROP_TABLE + FavoritesContract.Provider.TABLE_NAME;

    public FavoritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_STOP);
        db.execSQL(SQL_CREATE_ROUTE);
        db.execSQL(SQL_CREATE_ARRIVAL);
        db.execSQL(SQL_CREATE_PROVIDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ARRIVAL);
        db.execSQL(SQL_DELETE_PROVIDER);
        db.execSQL(SQL_DELETE_STOP);
        db.execSQL(SQL_DELETE_ROUTE);
        onCreate(db);
    }
}
