package net.spaceboats.busbus.android.DbHelper;

import android.content.Context;
import android.util.Log;

import net.spaceboats.busbus.android.Arrival;
import net.spaceboats.busbus.android.Entity;
import net.spaceboats.busbus.android.Route;
import net.spaceboats.busbus.android.Stop;

import java.util.List;

/**
 * Created by Zane on 3/18/2015.
 */
public class EntityDbDelegator {

    private static List<Entity> arrivals;
    private boolean dbUpdated;
    private RouteDbOperator routeDbOperator;
    private StopDbOperator stopDbOperator;
    private ArrivalDbOperator arrivalDbOperator;

    public EntityDbDelegator(Context context) {
        routeDbOperator = new RouteDbOperator(context);
        stopDbOperator = new StopDbOperator(context);
        arrivalDbOperator = new ArrivalDbOperator(context);
        dbUpdated = true;
    }

    public void insert(List<Entity> entities) {
        for(int i = 0; i < entities.size(); i++) {
            insert(entities.get(i));
        }
    }

    public void insert(Entity entity) {
        dbUpdated = true;
        if(Route.class.isInstance(entity))
            insertRoute((Route) entity);
        else if(Stop.class.isInstance(entity))
            insertStop((Stop) entity);
        else if(Arrival.class.isInstance(entity))
            insertArrival((Arrival) entity);
        else
            Log.v("DbHelper/DbDelegator", "Entity was an unknown type");
    }

    public List<Entity> queryArrivals() {
        if(dbUpdated)
            arrivals = arrivalDbOperator.query();
        return arrivals;
    }

    private void insertRoute(Route route) {
        routeDbOperator.insertAsTransaction(route);
    }

    private void insertStop(Stop stop) {
        stopDbOperator.insertAsTransaction(stop);
    }

    private void insertArrival(Arrival arrival) {
        arrivalDbOperator.insertAsTransaction(arrival);
    }
}
