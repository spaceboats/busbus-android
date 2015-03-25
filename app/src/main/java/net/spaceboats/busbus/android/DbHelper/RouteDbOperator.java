package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Route;

/**
 * Created by zralston on 3/15/15.
 */
public class RouteDbOperator extends BaseDbOperator {

    public RouteDbOperator(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return FavoritesContract.Route.TABLE_NAME;
    }

    @Override
    protected ContentValues getContentValues(Entity entity) {
        if(!Route.class.isInstance(entity))
            throw new IllegalArgumentException("Entity is not of type Route");

        Route route = (Route) entity;

        ContentValues values = new ContentValues();
        values.put(FavoritesContract.Route.COLUMN_ID, route.getId());
        values.put(FavoritesContract.Route.COLUMN_NAME, route.getName());
        values.put(FavoritesContract.Route.COLUMN_SHORT_NAME, route.getNumber());
        values.put(FavoritesContract.Route.COLUMN_COLOR, route.getColor());

        return values;
    }

    @Override
    protected Entity getNewEntity(Cursor cursor) {
        Route route = new Route(cursor.getString(cursor.getColumnIndex(FavoritesContract.Route.COLUMN_SHORT_NAME)),
                cursor.getString(cursor.getColumnIndex(FavoritesContract.Route.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(FavoritesContract.Route.COLUMN_COLOR)),
                cursor.getString(cursor.getColumnIndex(FavoritesContract.Route.COLUMN_ID)));

        return route;
    }

    @Override
    protected String[] getColumns() {
        String[] columns = {FavoritesContract.Route.COLUMN_ID,
                FavoritesContract.Route.COLUMN_NAME,
                FavoritesContract.Route.COLUMN_SHORT_NAME,
                FavoritesContract.Route.COLUMN_COLOR};

        return columns;
    }

    @Override
    protected String getIdSelection() {
        return FavoritesContract.Route.COLUMN_ID + "= ?";
    }
}
