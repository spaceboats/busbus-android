package net.spaceboats.busbus.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agustafs on 3/10/15.
 */
public class TheJSONParser {

    public static final String ROUTES_JSON_ID = "routes";
    public static final String ROUTE_JSON_ID = "route";
    public static final String ROUTE_DESCRIPTION_JSON_ID = "description";
    public static final String ROUTE_JSON_SHORT_NAME = "short_name";
    public static final String ROUTE_JSON_NAME = "name";
    public static final String ROUTE_JSON_NUMBER = "id";
    public static final String ROUTE_JSON_COLOR = "color";

    public static final String STOP_JSON_MYID = "id";
    public static final String STOPS_JSON_ID = "stops";
    public static final String STOP_JSON_ID = "stop";
    public static final String STOP_JSON_NAME = "name";
    public static final String STOP_JSON_DESCRIPTION = "description";
    public static final String STOP_JSON_LATITUDE = "latitude";
    public static final String STOP_JSON_LONGITUDE = "longitude";
    public static final String ARRIVALS_JSON_ID = "arrivals";
    public static final String ARRIVAL_JSON_HEADSIGN = "headsign";
    public static final String ARRIVAL_JSON_TIME = "time";


    public static List<Entity> getRouteList(String data)
        throws JSONException {

        JSONObject dataObj = new JSONObject(data);
        JSONArray routesArray = dataObj.getJSONArray(ROUTES_JSON_ID);
        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < routesArray.length(); i++) {
            JSONObject routeObj = routesArray.getJSONObject(i);
            entities.add(getRoute(routeObj));
        }

        return entities;
    }

    private static Route getRoute(JSONObject routeObj)
        throws JSONException {
        if (routeObj.isNull(ROUTE_JSON_SHORT_NAME) || routeObj.isNull(ROUTE_JSON_NAME))
            return null;
        String routeName = routeObj.getString(ROUTE_JSON_NAME);
        String routeNumber = routeObj.getString(ROUTE_JSON_SHORT_NAME);
        String routeColor = "#424242";
        if (!routeObj.isNull(ROUTE_JSON_COLOR))
            routeColor = "#" + routeObj.getString(ROUTE_JSON_COLOR);
        String routeId = routeObj.getString(ROUTE_JSON_NUMBER);
        return new Route(routeNumber, routeName, routeColor, routeId);
    }

    public static List<Entity> getStopList(String data)
        throws JSONException {

        JSONObject dataObj = new JSONObject(data);
        JSONArray stopsArray = dataObj.getJSONArray(STOPS_JSON_ID);
        List<Entity> entities = new ArrayList<>();
        for(int i = 0; i < stopsArray.length(); i++) {
            JSONObject stopObj = stopsArray.getJSONObject(i);
            entities.add(getStop(stopObj));
        }

        return entities;
    }

    private static Stop getStop(JSONObject stopObj)
        throws JSONException {
        String stopName = null;
        String stopDescription = null;
        if (!stopObj.isNull(STOP_JSON_NAME))
            stopName = stopObj.getString(STOP_JSON_NAME);
        if (!stopObj.isNull(STOP_JSON_DESCRIPTION))
            stopDescription = stopObj.getString(STOP_JSON_DESCRIPTION);
        double latitude = stopObj.getDouble(STOP_JSON_LATITUDE);
        double longitude = stopObj.getDouble(STOP_JSON_LONGITUDE);
        String stopId = stopObj.getString(STOP_JSON_MYID);
        return new Stop(stopName, latitude, longitude, stopDescription, stopId);
    }

    public static List<Entity> getArrivalList(String data)
        throws JSONException {

        JSONObject dataObj = new JSONObject(data);
        JSONArray arrivalsArray = dataObj.getJSONArray(ARRIVALS_JSON_ID);
        List<Entity> entities = new ArrayList<>();
        for(int i = 0; i < arrivalsArray.length(); i++) {
            JSONObject arrivalObj = arrivalsArray.getJSONObject(i);
            entities.add(getArrival(arrivalObj));
        }

        return entities;
    }

    private static Arrival getArrival(JSONObject arrivalObj)
        throws JSONException {

        JSONObject stopObj = arrivalObj.getJSONObject(STOP_JSON_ID);
        JSONObject routeObj = arrivalObj.getJSONObject(ROUTE_JSON_ID);
        Route route = getRoute(routeObj);
        if(route == null)
            return null;
        int time = arrivalObj.getInt(ARRIVAL_JSON_TIME);
        String headSign = null;
        if(!arrivalObj.isNull(ARRIVAL_JSON_HEADSIGN))
            headSign = arrivalObj.getString(ARRIVAL_JSON_HEADSIGN);
        return new Arrival(time, headSign, getStop(stopObj), route);
    }
}
