package net.spaceboats.busbus.android.Utils;

/**
 * Created by Zane on 4/1/2015.
 */
final class JSONKeys {

    public JSONKeys() {
    }

    /*
    Different entity types available
     */
    public static final String ENTITY_STOPS = "stops";
    public static final String ENTITY_ROUTES = "routes";
    public static final String ENTITY_ARRIVALS = "arrivals";
    public static final String ENTITY_PROVIDERS = "providers";

    /*
    Common attribute keys across entities
    Note: Each entity should references these at the start. If a specific entity were to change the
    common key name, then only that entity type should be updated below.
     */
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";

    /*
    Provider attributes
     */
    public static final String PROVIDER = "provider";
    public static final String PROVIDER_ID = ID;
    public static final String PROVIDER_CREDIT = "credit";
    public static final String PROVIDER_COUNTRY = "country";
    public static final String PROVIDER_CREDIT_URL = "credit_url";

    /*
    Stop attributes
     */
    public static final String STOP = "stop";
    public static final String STOP_ID = ID;
    public static final String STOP_URL = "url";
    public static final String STOP_NAME = NAME;
    public static final String STOP_CODE = "code";
    public static final String STOP_TIMEZONE = "timezone";
    public static final String STOP_LATITUDE = "latitude";
    public static final String STOP_LONGITUDE = "longitude";
    public static final String STOP_DESCRIPTION = DESCRIPTION;
    public static final String STOP_DISTANCE = "distance";
    public static final String STOP_DISTANCE_PATH_EXTENSION = "find";

    /*
    Route attributes
     */
    public static final String ROUTE = "route";
    public static final String ROUTE_ID = ID;
    public static final String ROUTE_NAME = NAME;
    public static final String ROUTE_COLOR = "color";
    public static final String ROUTE_AGENCY = "agency";
    public static final String ROUTE_SHORT_NAME = "short_name";
    public static final String ROUTE_DESCRIPTION = DESCRIPTION;

    /*
    Arrival attributes
     */
    public static final String ARRIVAL = "arrival";
    public static final String ARRIVAL_STOP = STOP;
    public static final String ARRIVAL_TIME = "time";
    public static final String ARRIVAL_ROUTE = ROUTE;
    public static final String ARRIVAL_PROVIDER = PROVIDER;
    public static final String ARRIVAL_HEADSIGN = "headsign";
}
