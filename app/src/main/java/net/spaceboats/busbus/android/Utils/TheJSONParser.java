package net.spaceboats.busbus.android.Utils;

import net.spaceboats.busbus.android.Entites.Arrival;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Provider;
import net.spaceboats.busbus.android.Entites.Route;
import net.spaceboats.busbus.android.Entites.Stop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agustafs on 3/10/15.
 */
public class TheJSONParser {

    public static List<Entity> getRouteList(String data)
        throws JSONException {

        JSONObject dataObj = new JSONObject(data);
        JSONArray routesArray = dataObj.getJSONArray(JSONKeys.ENTITY_ROUTES);
        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < routesArray.length(); i++) {
            JSONObject routeObj = routesArray.getJSONObject(i);
            entities.add(getRoute(routeObj));
        }

        return entities;
    }

    private static Route getRoute(JSONObject routeObj)
        throws JSONException {
        String routeName = "N/A";
        String routeNumber = "N/A";
        String routeColor = "#424242";
        if(!routeObj.isNull(JSONKeys.ROUTE_SHORT_NAME)) {
            routeNumber = routeObj.getString(JSONKeys.ROUTE_SHORT_NAME);
        }
        if(!routeObj.isNull(JSONKeys.ROUTE_NAME)) {
            routeName = routeObj.getString(JSONKeys.ROUTE_NAME);
        }
        if(!routeObj.isNull(JSONKeys.ROUTE_COLOR)) {
            routeColor = "#" + routeObj.getString(JSONKeys.ROUTE_COLOR);
        }
        String routeId = routeObj.getString(JSONKeys.ROUTE_ID);
        // TODO: Fix this by just attaching provider object?
        JSONObject providerObj = routeObj.getJSONObject(JSONKeys.ROUTE_PROVIDER);
        String providerId = providerObj.getString(JSONKeys.PROVIDER_ID);
        return new Route(routeNumber, routeName, routeColor, routeId, providerId);
    }

    public static List<Entity> getStopList(String data)
        throws JSONException {

        JSONObject dataObj = new JSONObject(data);
        JSONArray stopsArray = dataObj.getJSONArray(JSONKeys.ENTITY_STOPS);
        List<Entity> entities = new ArrayList<>();
        for(int i = 0; i < stopsArray.length(); i++) {
            JSONObject stopObj = stopsArray.getJSONObject(i);
            entities.add(getStop(stopObj));
        }

        return entities;
    }

    private static Stop getStop(JSONObject stopObj)
        throws JSONException {
        String stopName = "";
        String stopDescription = "";
        if (!stopObj.isNull(JSONKeys.STOP_NAME))
            stopName = stopObj.getString(JSONKeys.STOP_NAME);
        if (!stopObj.isNull(JSONKeys.STOP_DESCRIPTION))
            stopDescription = stopObj.getString(JSONKeys.STOP_DESCRIPTION);
        double latitude = stopObj.getDouble(JSONKeys.STOP_LATITUDE);
        double longitude = stopObj.getDouble(JSONKeys.STOP_LONGITUDE);
        String stopId = stopObj.getString(JSONKeys.STOP_ID);
        // TODO: Fix this by just attaching provider object?
        JSONObject providerObj = stopObj.getJSONObject(JSONKeys.STOP_PROVIDER);
        String providerId = providerObj.getString(JSONKeys.PROVIDER_ID);
        return new Stop(stopName, latitude, longitude, stopDescription, stopId, providerId);
    }

    public static List<Entity> getArrivalList(String data)
        throws JSONException {

        JSONObject dataObj = new JSONObject(data);
        JSONArray arrivalsArray = dataObj.getJSONArray(JSONKeys.ENTITY_ARRIVALS);
        List<Entity> entities = new ArrayList<>();
        for(int i = 0; i < arrivalsArray.length(); i++) {
            JSONObject arrivalObj = arrivalsArray.getJSONObject(i);
            entities.add(getArrival(arrivalObj));
        }

        return entities;
    }

    private static Arrival getArrival(JSONObject arrivalObj)
        throws JSONException {

        JSONObject stopObj = arrivalObj.getJSONObject(JSONKeys.ARRIVAL_STOP);
        JSONObject routeObj = arrivalObj.getJSONObject(JSONKeys.ARRIVAL_ROUTE);
        Route route = getRoute(routeObj);
        int time = arrivalObj.getInt(JSONKeys.ARRIVAL_TIME);
        String headSign = "N/A";
        if(!arrivalObj.isNull(JSONKeys.ARRIVAL_HEADSIGN))
            headSign = arrivalObj.getString(JSONKeys.ARRIVAL_HEADSIGN);
        return new Arrival(time, headSign, getStop(stopObj), route);
    }

    public static List<Entity> getProviderList(String data)
        throws JSONException {

        JSONObject dataObj = new JSONObject(data);
        JSONArray providersArray = dataObj.getJSONArray(JSONKeys.ENTITY_PROVIDERS);
        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < providersArray.length(); i++) {
            JSONObject providerObj = providersArray.getJSONObject(i);
            entities.add(getProvider(providerObj));
        }

        return entities;
    }

    private static Provider getProvider(JSONObject providerObj)
            throws JSONException {
        String providerCredit = providerObj.getString(JSONKeys.PROVIDER_CREDIT);
        String providerId = providerObj.getString(JSONKeys.PROVIDER_ID);
        return new Provider(providerCredit, providerId);
    }
}
