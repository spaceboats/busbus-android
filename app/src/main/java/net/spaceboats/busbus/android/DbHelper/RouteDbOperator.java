package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.spaceboats.busbus.android.Entity;
import net.spaceboats.busbus.android.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zralston on 3/15/15.
 */
public class RouteDbOperator {

    Context mContext;

    public RouteDbOperator(Context context) {
        mContext = context;
    }

    public void insert(Route route) {
        List<Route> routes = new ArrayList<>();
        routes.add(route);
        insert(routes);
    }

    public void insert(List<Route> routes) {
        FavoritesDbHelper sqlHelper = new FavoritesDbHelper(mContext);
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            insert(routes, db);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
    }

    public void insert(Route route, SQLiteDatabase db) {
        List<Route> routes = new ArrayList<>();
        routes.add(route);
        insert(routes, db);
    }

    public void insert(List<Route> routes, SQLiteDatabase db) {
        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);

            ContentValues values = new ContentValues();
            values.put(FavoritesContract.Route.COLUMN_ID, route.getId());
            values.put(FavoritesContract.Route.COLUMN_NAME, route.getName());
            values.put(FavoritesContract.Route.COLUMN_SHORT_NAME, route.getNumber());
            values.put(FavoritesContract.Route.COLUMN_COLOR, route.getColor());

            db.insert(FavoritesContract.Route.TABLE_NAME, null, values);
        }
    }

    public List<Entity> query() {
        List<Entity> entities = new ArrayList<>();
        FavoritesDbHelper sqlHelper = new FavoritesDbHelper(mContext);
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        String[] columns = {FavoritesContract.Route.COLUMN_ID,
                            FavoritesContract.Route.COLUMN_NAME,
                            FavoritesContract.Route.COLUMN_SHORT_NAME,
                            FavoritesContract.Route.COLUMN_COLOR};

        Cursor cursor = db.query(FavoritesContract.Route.TABLE_NAME, columns, null, null, null, null, null);
        while(cursor.moveToNext()) {
            Route route = new Route(cursor.getString(cursor.getColumnIndex(FavoritesContract.Route.COLUMN_SHORT_NAME)),
                                    cursor.getString(cursor.getColumnIndex(FavoritesContract.Route.COLUMN_NAME)),
                                    cursor.getString(cursor.getColumnIndex(FavoritesContract.Route.COLUMN_COLOR)),
                                    cursor.getString(cursor.getColumnIndex(FavoritesContract.Route.COLUMN_ID)));
            entities.add(route);
        }
        cursor.close();

        return entities;
    }
}
