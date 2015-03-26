package net.spaceboats.busbus.android.DbHelper;

import android.content.Context;
import android.util.Log;

import net.spaceboats.busbus.android.Entites.Arrival;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Route;
import net.spaceboats.busbus.android.Entites.Stop;

import java.util.List;

/**
 * Created by Zane on 3/18/2015.
 */
public class EntityDbDelegator {

    private static List<Entity> sArrivals;
    private static boolean sDbUpdated;
    private static RouteDbOperator sRouteDbOperator;
    private static StopDbOperator sStopDbOperator;
    private static ArrivalDbOperator sArrivalDbOperator;

    public static void initDbDelegator(Context context) {
        if(!isInitialized()) {
            sRouteDbOperator = new RouteDbOperator(context);
            sStopDbOperator = new StopDbOperator(context);
            sArrivalDbOperator = new ArrivalDbOperator(context);
            sDbUpdated = true;
        }
    }

    public static boolean isInitialized() {
        return sRouteDbOperator != null;
    }

    public static void insert(List<Entity> entities) {
        for(int i = 0; i < entities.size(); i++) {
            insert(entities.get(i));
        }
    }

    public static void insert(Entity entity) {
        sDbUpdated = true;
        if(Route.class.isInstance(entity))
            insertRoute((Route) entity);
        else if(Stop.class.isInstance(entity))
            insertStop((Stop) entity);
        else if(Arrival.class.isInstance(entity))
            insertArrival((Arrival) entity);
        else
            Log.v("DbHelper/DbDelegator", "Entity was an unknown type");
    }

    public static void delete(Entity entity) {
        sDbUpdated = true;
        sDbUpdated = true;
        if(Route.class.isInstance(entity))
            deleteRoute((Route) entity);
        else if(Stop.class.isInstance(entity))
            deleteStop((Stop) entity);
        else if(Arrival.class.isInstance(entity))
            deleteArrival((Arrival) entity);
        else
            Log.v("DbHelper/DbDelegator", "Entity was an unknown type");
    }

    public static List<Entity> queryArrivals() {
        if(sDbUpdated)
            sArrivals = sArrivalDbOperator.query();
        return sArrivals;
    }

    private static void insertRoute(Route route) {
        sRouteDbOperator.insertAsTransaction(route);
    }

    private static void insertStop(Stop stop) {
        sStopDbOperator.insertAsTransaction(stop);
    }

    private static void insertArrival(Arrival arrival) {
        sArrivalDbOperator.insertAsTransaction(arrival);
    }

    private static void deleteRoute(Route route) {
        sRouteDbOperator.deleteAsTransaction(route);
    }

    private static void deleteStop(Stop stop) {
        sStopDbOperator.deleteAsTransaction(stop);
    }

    private static void deleteArrival(Arrival arrival) {
        sArrivalDbOperator.deleteAsTransaction(arrival);
    }
}
