package net.spaceboats.busbus.android.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Zane on 3/17/2015.
 */
class DbManager {

    private static int sOpenCounter;

    private static SQLiteDatabase sDatabase;
    private static FavoritesDbHelper sDbHelper;

    public static void initDb(Context context) {
        if(isInitialized()) {
            Log.v(DbManager.class.getName(), "DbManager was already initialized");
            return;
        }
        sDbHelper = new FavoritesDbHelper(context);
        sOpenCounter = 0;
        sDatabase = null;
    }

    public static boolean isInitialized() {
        return sDbHelper != null;
    }

    public static SQLiteDatabase getDatabase() {
        if(!isInitialized()) {
            throw new IllegalStateException("Make sure to call initDB first");
        }

        sOpenCounter++;
        if(sDatabase == null) {
            sDatabase = sDbHelper.getWritableDatabase();
        }

        return sDatabase;
    }

    public static void closeDatabase() {
        if(sDatabase == null) {
            throw new IllegalStateException("Database was never initialized. Probably never called getDatabase()");
        }

        sOpenCounter--;
        if(sOpenCounter <= 0) {
            sDatabase.close();
            sDatabase = null;
        }
    }
}
