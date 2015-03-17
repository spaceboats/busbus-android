package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.spaceboats.busbus.android.Arrival;
import net.spaceboats.busbus.android.Entity;
import net.spaceboats.busbus.android.Route;
import net.spaceboats.busbus.android.Stop;

import java.util.List;

/**
 * Created by zralston on 3/15/15.
 */
public class ArrivalDbOperator extends BaseDbOperator {

    public ArrivalDbOperator(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return FavoritesContract.Arrival.TABLE_NAME;
    }

    @Override
    protected ContentValues getContentValues(Entity entity) {
        if(!Arrival.class.isInstance(entity))
            throw new IllegalArgumentException("Entity is not of type Arrival");

        Arrival arrival = (Arrival) entity;

        ContentValues values = new ContentValues();
        values.put(FavoritesContract.Arrival.COLUMN_ROUTE_ID, arrival.getRoute().getId());
        values.put(FavoritesContract.Arrival.COLUMN_STOP_ID, arrival.getStop().getId());

        return values;
    }

    @Override
    protected Entity getNewEntity(Cursor cursor, SQLiteDatabase db) {
        RouteDbOperator routeDbOperator = new RouteDbOperator(mContext);
        StopDbOperator stopDbOperator = new StopDbOperator(mContext);

        String id = cursor.getString(cursor.getColumnIndex(FavoritesContract.Arrival.COLUMN_ROUTE_ID));

        Route route = (Route) routeDbOperator.queryWithId(db, cursor.getString(cursor.getColumnIndex(FavoritesContract.Arrival.COLUMN_ROUTE_ID)));
        Stop stop = (Stop) stopDbOperator.queryWithId(db, cursor.getString(cursor.getColumnIndex(FavoritesContract.Arrival.COLUMN_STOP_ID)));

        return new Arrival(-1, null, stop, route);
    }

    @Override
    protected String[] getColumns() {
        String[] columns = {FavoritesContract.Arrival.COLUMN_ROUTE_ID,
                FavoritesContract.Arrival.COLUMN_STOP_ID };

        return columns;
    }

    @Override
    public void insert(List<Entity> arrivals) {
        FavoritesDbHelper sqlHelper = new FavoritesDbHelper(mContext);
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (int i = 0; i < arrivals.size(); i++) {
                Arrival arrival = (Arrival)arrivals.get(i);

                // TODO: Fix the fact that this trys to insert something that is already in the database
                RouteDbOperator routeDbOperator = new RouteDbOperator(mContext);
                StopDbOperator stopDbOperator = new StopDbOperator(mContext);
                routeDbOperator.insert(arrival.getRoute(), db);
                stopDbOperator.insert(arrival.getStop(), db);

                ContentValues values = new ContentValues();
                values.put(FavoritesContract.Arrival.COLUMN_ROUTE_ID, arrival.getRoute().getId());
                values.put(FavoritesContract.Arrival.COLUMN_STOP_ID, arrival.getStop().getId());

                db.insert(FavoritesContract.Arrival.TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
    }

}
