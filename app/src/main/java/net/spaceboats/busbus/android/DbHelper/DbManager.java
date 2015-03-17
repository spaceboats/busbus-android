package net.spaceboats.busbus.android.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Zane on 3/17/2015.
 */
public class DbManager {

    static int mOpenCounter;

    private static SQLiteDatabase mDatabase;
    private static FavoritesDbHelper mDbHelper;

    public static void initDb(Context context) {
        if(mDbHelper != null) {
            Log.v("DbManager", "DbManager was already initialized");
            return;
        }
        mDbHelper = new FavoritesDbHelper(context);
        mOpenCounter = 0;
        mDatabase = null;
    }

    public static SQLiteDatabase getDatabase() {
        if(mDbHelper == null) {
            throw new IllegalStateException("Make sure to call initDB first");
        }

        mOpenCounter++;
        if(mDatabase == null) {
            mDatabase = mDbHelper.getWritableDatabase();
        }

        return mDatabase;
    }

    public static void closeDatabase() {
        if(mDatabase == null) {
            throw new IllegalStateException("Database was never initialized. Probably never called getDatabse()");
        }

        mOpenCounter--;
        if(mOpenCounter <= 0) {
            mDatabase.close();
            mDatabase = null;
        }
    }
}
