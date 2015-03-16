package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.spaceboats.busbus.android.Stop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zralston on 3/15/15.
 */
public class StopDbOperator {

    Context mContext;

    public StopDbOperator(Context context) {
        mContext = context;
    }

    public void insert(Stop stop) {
        List<Stop> stops = new ArrayList<>();
        stops.add(stop);
        insert(stop);
    }

    public void insert(List<Stop> stops) {
        FavoritesDbHelper sqlHelper = new FavoritesDbHelper(mContext);
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (int i = 0; i < stops.size(); i++) {
                Stop stop = stops.get(i);

                ContentValues values = new ContentValues();
                values.put(FavoritesContract.Stop.COLUMN_ID, stop.getId());
                values.put(FavoritesContract.Stop.COLUMN_NAME, stop.getStopName());
                values.put(FavoritesContract.Stop.COLUMN_LATITUDE, stop.getLatitude());
                values.put(FavoritesContract.Stop.COLUMN_LONGITUDE, stop.getLongitude());
                values.put(FavoritesContract.Stop.COLUMN_DESCRIPTION, stop.getDescription());

                db.insert(FavoritesContract.Stop.TABLE_NAME, null, values);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
    }
}
