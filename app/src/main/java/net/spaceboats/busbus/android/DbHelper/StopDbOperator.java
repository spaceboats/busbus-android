package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Stop;

/**
 * Created by zralston on 3/15/15.
 */
public class StopDbOperator extends BaseDbOperator<Stop> {

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

        return values;
    }

    @Override
    protected Stop getNewEntity(Cursor cursor) {
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
                FavoritesContract.Stop.COLUMN_DESCRIPTION};
        return columns;
    }

    @Override
    protected String getIdSelection() {
        return FavoritesContract.Stop.COLUMN_ID + "= ?";
    }

    @Override
    protected String getDeleteWhereClause() {
        return FavoritesContract.Stop.COLUMN_ID + " = ? and "
                + FavoritesContract.Stop.COLUMN_NAME + " = ? and "
                + FavoritesContract.Stop.COLUMN_LATITUDE + " = ? and "
                + FavoritesContract.Stop.COLUMN_LONGITUDE + " = ? and "
                + FavoritesContract.Stop.COLUMN_DESCRIPTION + " = ?";
    }

    @Override
    protected String[] getDeleteWhereArgs(Stop stop) {
        // TODO: Decouple the order of this with delete where clause somehow. Since they both rely on same ordering
        String[] args = {stop.getId(),
                stop.getStopName(),
                Double.toString(stop.getLatitude()),
                Double.toString(stop.getLongitude()),
                stop.getDescription()};
        return args;
    }
}
