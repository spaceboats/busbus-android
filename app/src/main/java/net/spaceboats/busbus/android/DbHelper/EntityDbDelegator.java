package net.spaceboats.busbus.android.DbHelper;

import android.content.Context;
import android.util.Log;

import net.spaceboats.busbus.android.Entites.Arrival;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Provider;
import net.spaceboats.busbus.android.Entites.Route;
import net.spaceboats.busbus.android.Entites.Stop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zane on 3/18/2015.
 */
public class EntityDbDelegator {

    // Each is used to cache to reduce the reads from the database
    private static List<Entity> sArrivals;
    private static List<Entity> sProviders;

    // Used to keep track of whether we should update the cached list above
    private static boolean sDbUpdated;

    // Different db operators specific to each type of entity
    private static RouteDbOperator sRouteDbOperator;
    private static StopDbOperator sStopDbOperator;
    private static ArrivalDbOperator sArrivalDbOperator;
    private static ProviderDbOperator sProviderDbOperator;

    public static void initDbDelegator(Context context) {
        if(!isInitialized()) {
            sRouteDbOperator = new RouteDbOperator(context);
            sStopDbOperator = new StopDbOperator(context);
            sArrivalDbOperator = new ArrivalDbOperator(context);
            sProviderDbOperator = new ProviderDbOperator(context);
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
        if(entity instanceof Route)
            insertRoute((Route) entity);
        else if(entity instanceof Stop)
            insertStop((Stop) entity);
        else if(entity instanceof Arrival)
            insertArrival((Arrival) entity);
        else if(entity instanceof Provider)
            insertProvider((Provider) entity);
        else
            Log.v(EntityDbDelegator.class.getName(), "Entity was an unknown type");
    }

    public static void delete(Entity entity) {
        sDbUpdated = true;
        if(entity instanceof Route)
            deleteRoute((Route) entity);
        else if(entity instanceof Stop)
            deleteStop((Stop) entity);
        else if(entity instanceof Arrival)
            deleteArrival((Arrival) entity);
        else if(entity instanceof Provider)
            deleteProvider((Provider) entity);
        else
            Log.v(EntityDbDelegator.class.getName(), "Entity was an unknown type");
    }

    public static List<Entity> queryArrivals() {
        updateCachedList();
        return sArrivals;
    }

    public static List<Entity> queryProviders() {
        updateCachedList();
        return sProviders;
    }

    public static List<Entity> queryFavorites() {
        List<Entity> favorites = new ArrayList<>();
        favorites.addAll(queryArrivals());
        favorites.addAll(queryProviders());
        return favorites;
    }

    private static void updateCachedList() {
        if(sDbUpdated) {
            sArrivals = sArrivalDbOperator.query();
            sProviders = sProviderDbOperator.query();
            sDbUpdated = false;
        }
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

    private static void insertProvider(Provider provider) {
        sProviderDbOperator.insertAsTransaction(provider);
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

    private static void deleteProvider(Provider provider) {
        sProviderDbOperator.deleteAsTransaction(provider);
    }
}
