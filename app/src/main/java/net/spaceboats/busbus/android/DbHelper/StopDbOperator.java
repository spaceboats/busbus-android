package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Stop;

/**
 * Created by zralston on 3/15/15.
 */
class StopDbOperator extends BaseDbOperator<Stop> {

    public StopDbOperator(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return FavoritesContract.Stop.TABLE_NAME;
    }

    @Override
    protected ContentValues getContentValues(Stop stop) {
        ContentValues values = new ContentValues();
        values.put(FavoritesContract.Stop.COLUMN_ID, stop.getId());
        values.put(FavoritesContract.Stop.COLUMN_NAME, stop.getStopName());
        values.put(FavoritesContract.Stop.COLUMN_LATITUDE, stop.getLatitude());
        values.put(FavoritesContract.Stop.COLUMN_LONGITUDE, stop.getLongitude());
        values.put(FavoritesContract.Stop.COLUMN_DESCRIPTION, stop.getDescription());
        values.put(FavoritesContract.Stop.COLUMN_PROVIDER_ID, stop.getProviderId());

        return values;
    }

    @Override
    protected Stop getNewEntity(Cursor cursor) {
        return new Stop(cursor.getString(cursor.getColumnIndex(FavoritesContract.Stop.COLUMN_NAME)),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavoritesContract.Stop.COLUMN_LATITUDE))),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavoritesContract.Stop.COLUMN_LONGITUDE))),
                cursor.getString(cursor.getColumnIndex(FavoritesContract.Stop.COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(FavoritesContract.Stop.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(FavoritesContract.Stop.COLUMN_PROVIDER_ID)));
    }

    @Override
    protected String[] getColumns() {
        return new String[]{FavoritesContract.Stop.COLUMN_ID,
                FavoritesContract.Stop.COLUMN_NAME,
                FavoritesContract.Stop.COLUMN_LATITUDE,
                FavoritesContract.Stop.COLUMN_LONGITUDE,
                FavoritesContract.Stop.COLUMN_DESCRIPTION,
                FavoritesContract.Stop.COLUMN_PROVIDER_ID};
    }

    @Override
    protected String getIdSelection() {
        return FavoritesContract.Stop.COLUMN_ID + "= ? and " + FavoritesContract.Stop.COLUMN_PROVIDER_ID + "= ?";
    }

}
