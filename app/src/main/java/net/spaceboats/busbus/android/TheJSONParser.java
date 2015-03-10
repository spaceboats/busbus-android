package net.spaceboats.busbus.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by agustafs on 3/10/15.
 */
public class TheJSONParser {

    public static final String STOPS_JSON_ID = "stops";
    public static final String ROUTES_JSON_ID = "routes";
    public static final String ROUTE_DESCRIPTION_JSON_ID = "description";
    public static final String ROUTE_JSON_SHORT_NAME = "short_name";
    public static final String ROUTE_JSON_NAME = "name";
    public static final String ROUTE_JSON_NUMBER = "id";
    public static final String ROUTE_JSON_COLOR = "color";

    public static void addRoutes(RouteRecyclerAdapter adapter, String data)
        throws JSONException {

        JSONObject dataObj = new JSONObject(data);

        JSONArray routesArray = dataObj.getJSONArray(ROUTES_JSON_ID);
        for (int i = 0; i < routesArray.length(); i++) {
            JSONObject route = routesArray.getJSONObject(i);
            if(route.isNull(ROUTE_JSON_SHORT_NAME) || route.isNull(ROUTE_JSON_NAME))
                continue;
            String routeName = route.getString(ROUTE_JSON_NAME);
            String routeNumber = "Route " + route.getString(ROUTE_JSON_SHORT_NAME);
            String routeColor = "#424242";
            if(!route.isNull(ROUTE_JSON_COLOR))
                routeColor = "#" + route.getString(ROUTE_JSON_COLOR);
            adapter.addRoute(i, new Route(routeNumber, routeName, routeColor));
        }
    }

    public void addStops(RouteRecyclerAdapter adapter, String data)
        throws JSONException {

        JSONObject dataObj = new JSONObject(data);
        JSONObject stops = dataObj.getJSONObject(STOPS_JSON_ID);
    }
}
