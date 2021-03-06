package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.spaceboats.busbus.android.Entites.Arrival;
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
        values.put(FavoritesContract.Arrival.COLUMN_ROUTE_ID, arrival.getRouteId());
        values.put(FavoritesContract.Arrival.COLUMN_STOP_ID, arrival.getStopId());
        values.put(FavoritesContract.Arrival.COLUMN_PROVIDER_ID, arrival.getProviderId());

        return values;
    }

    @Override
    protected Arrival getNewEntity(Cursor cursor) {
        String routeId = cursor.getString(cursor.getColumnIndex(FavoritesContract.Arrival.COLUMN_ROUTE_ID));
        String stopId = cursor.getString(cursor.getColumnIndex(FavoritesContract.Arrival.COLUMN_STOP_ID));
        String providerId = cursor.getString(cursor.getColumnIndex(FavoritesContract.Arrival.COLUMN_PROVIDER_ID));

        RouteDbOperator routeDbOperator = new RouteDbOperator(mContext);
        StopDbOperator stopDbOperator = new StopDbOperator(mContext);

        // Assuming the arrival has the same provider has the stop and route.
        // TODO: Come up with way to not have these depending on the same order as getIdSelection() function for route and stop
        String[] routeArgs = {routeId, providerId};
        String[] stopArgs = {stopId, providerId};

        Route route = routeDbOperator.queryWithIds(routeArgs);
        Stop stop = stopDbOperator.queryWithIds(stopArgs);

        return new Arrival(-1, "", stop, route);
    }

    @Override
    protected String[] getColumns() {
        return new String[]{FavoritesContract.Arrival.COLUMN_ROUTE_ID,
                FavoritesContract.Arrival.COLUMN_STOP_ID,
                FavoritesContract.Arrival.COLUMN_PROVIDER_ID};
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

}
