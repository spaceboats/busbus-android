package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.spaceboats.busbus.android.Arrival;
import net.spaceboats.busbus.android.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zralston on 3/15/15.
 */
public class ArrivalDbOperator {
    Context mContext;

    public ArrivalDbOperator(Context context) {
        mContext = context;
    }

    public void insert(Entity entity) {
        List<Entity> entities = new ArrayList<>();
        entities.add(entity);
        insert(entities);
    }

    public void insert(List<Entity> arrivals) {
        FavoritesDbHelper sqlHelper = new FavoritesDbHelper(mContext);
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (int i = 0; i < arrivals.size(); i++) {
                Arrival arrival = (Arrival)arrivals.get(i);

                // TODO: See if this actually works, since both of these call getWritableDatabase as well.
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
