package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.spaceboats.busbus.android.Entites.Arrival;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Route;
import net.spaceboats.busbus.android.Entites.Stop;

/**
 * Created by zralston on 3/15/15.
 */
class ArrivalDbOperator extends BaseDbOperator<Arrival> {

    public ArrivalDbOperator(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return FavoritesContract.Arrival.TABLE_NAME;
    }

    @Override
    protected ContentValues getContentValues(Arrival arrival) {
        ContentValues values = new ContentValues();
        values.put(FavoritesContract.Arrival.COLUMN_ROUTE_ID, arrival.getRoute().getId());
        values.put(FavoritesContract.Arrival.COLUMN_STOP_ID, arrival.getStop().getId());

        return values;
    }

    @Override
    protected Arrival getNewEntity(Cursor cursor) {
        RouteDbOperator routeDbOperator = new RouteDbOperator(mContext);
        StopDbOperator stopDbOperator = new StopDbOperator(mContext);

        Route route = routeDbOperator.queryWithId(cursor.getString(cursor.getColumnIndex(FavoritesContract.Arrival.COLUMN_ROUTE_ID)));
        Stop stop = stopDbOperator.queryWithId(cursor.getString(cursor.getColumnIndex(FavoritesContract.Arrival.COLUMN_STOP_ID)));

        return new Arrival(-1, "", stop, route);
    }

    @Override
    protected String[] getColumns() {
        String[] columns = {FavoritesContract.Arrival.COLUMN_ROUTE_ID,
                FavoritesContract.Arrival.COLUMN_STOP_ID };

        return columns;
    }

    @Override
    public void insertSubEntities(Arrival arrival) {
        RouteDbOperator routeDbOperator = new RouteDbOperator(mContext);
        StopDbOperator stopDbOperator = new StopDbOperator(mContext);
        routeDbOperator.insert(arrival.getRoute());
        stopDbOperator.insert(arrival.getStop());
    }

    @Override
    protected String getIdSelection() {
        return "";
    }

    @Override
    protected String getDeleteWhereClause() {
        return FavoritesContract.Arrival.COLUMN_ROUTE_ID + " = ? and "
                + FavoritesContract.Arrival.COLUMN_STOP_ID + " = ?";
    }

    @Override
    protected String[] getDeleteWhereArgs(Arrival arrival) {
        // TODO: Decouple the order of this with delete where clause somehow. Since they both rely on same ordering
        String[] args = {arrival.getRoute().getId(), arrival.getStop().getId()};
        return args;
    }
}
