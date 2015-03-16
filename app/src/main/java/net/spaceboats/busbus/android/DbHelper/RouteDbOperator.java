package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
    }
}
