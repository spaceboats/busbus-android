package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.spaceboats.busbus.android.Entity;
import net.spaceboats.busbus.android.Stop;

/**
 * Created by zralston on 3/15/15.
 */
public class StopDbOperator extends BaseDbOperator {

    public StopDbOperator(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return FavoritesContract.Stop.TABLE_NAME;
    }

    @Override
    protected ContentValues getContentValues(Entity entity) {
        if(!Stop.class.isInstance(entity))
            throw new IllegalArgumentException("Entity is not of type Stop");

        Stop stop = (Stop) entity;

        ContentValues values = new ContentValues();
        values.put(FavoritesContract.Stop.COLUMN_ID, stop.getId());
        values.put(FavoritesContract.Stop.COLUMN_NAME, stop.getStopName());
        values.put(FavoritesContract.Stop.COLUMN_LATITUDE, stop.getLatitude());
        values.put(FavoritesContract.Stop.COLUMN_LONGITUDE, stop.getLongitude());
        values.put(FavoritesContract.Stop.COLUMN_DESCRIPTION, stop.getDescription());

        return values;
    }

    @Override
    protected Entity getNewEntity(Cursor cursor) {
        Stop stop = new Stop(cursor.getString(cursor.getColumnIndex(FavoritesContract.Stop.COLUMN_NAME)),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavoritesContract.Stop.COLUMN_LATITUDE))),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavoritesContract.Stop.COLUMN_LONGITUDE))),
                cursor.getString(cursor.getColumnIndex(FavoritesContract.Stop.COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(FavoritesContract.Stop.COLUMN_ID)));

        return stop;
    }

    @Override
    protected String[] getColumns() {
        String[] columns = {FavoritesContract.Stop.COLUMN_ID,
                FavoritesContract.Stop.COLUMN_NAME,
                FavoritesContract.Stop.COLUMN_LATITUDE,
                FavoritesContract.Stop.COLUMN_LONGITUDE,
                FavoritesContract.Stop.COLUMN_DESCRIPTION };
        return columns;
    }

    @Override
    protected String getIdSelection() {
        return FavoritesContract.Stop.COLUMN_ID + "= ?";
    }
}
