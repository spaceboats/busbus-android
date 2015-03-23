package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.spaceboats.busbus.android.Arrival;
import net.spaceboats.busbus.android.Entity;
import net.spaceboats.busbus.android.Route;
import net.spaceboats.busbus.android.Stop;

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
    protected Entity getNewEntity(Cursor cursor) {
        RouteDbOperator routeDbOperator = new RouteDbOperator(mContext);
        StopDbOperator stopDbOperator = new StopDbOperator(mContext);

        Route route = (Route) routeDbOperator.queryWithId(cursor.getString(cursor.getColumnIndex(FavoritesContract.Arrival.COLUMN_ROUTE_ID)));
        Stop stop = (Stop) stopDbOperator.queryWithId(cursor.getString(cursor.getColumnIndex(FavoritesContract.Arrival.COLUMN_STOP_ID)));

        return new Arrival(-1, "", stop, route);
    }

    @Override
    protected String[] getColumns() {
        String[] columns = {FavoritesContract.Arrival.COLUMN_ROUTE_ID,
                FavoritesContract.Arrival.COLUMN_STOP_ID };

        return columns;
    }

    @Override
    public void insertSubEntities(Entity entity) {
        if(!Arrival.class.isInstance(entity))
            throw new IllegalArgumentException("Entity is not of type Arrival");

        Arrival arrival = (Arrival) entity;

        RouteDbOperator routeDbOperator = new RouteDbOperator(mContext);
        StopDbOperator stopDbOperator = new StopDbOperator(mContext);
        routeDbOperator.insert(arrival.getRoute());
        stopDbOperator.insert(arrival.getStop());
    }

    @Override
    protected String getIdSelection() {
        return "";
    }
}
