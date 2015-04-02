package net.spaceboats.busbus.android.Utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zralston on 4/1/15.
 */
public class ArrivalURLBuilder extends URLBuilder {

    protected static final String ARRIVALS_LIMIT = "_limit";
    protected static final String ARRIVALS_START_TIME = "start_time";
    protected static final String ARRIVALS_END_TIME = "end_time";

    public ArrivalURLBuilder(Context context) {
        super(context, JSONKeys.ENTITY_ARRIVALS);
    }

    public void addLimit(String amount) {
        addQueryParam(ARRIVALS_LIMIT, amount);
    }

    public void addStartTime(String time) {
        addQueryParam(ARRIVALS_START_TIME, time);
    }

    public void addEndTime(String time) {
        addQueryParam(ARRIVALS_END_TIME, time);
    }

    public void addStopId(String id) {
        addEntityAttrParam(JSONKeys.STOP, JSONKeys.STOP_ID, id);
    }

    public void addRouteId(String id) {
        addEntityAttrParam(JSONKeys.ROUTE, JSONKeys.ROUTE_ID, id);
    }

    public void expandRoute() {
        addToExpandArgs(JSONKeys.ENTITY_ROUTES);
    }

    public void expandStop() {
        addToExpandArgs(JSONKeys.ENTITY_STOPS);
    }

    public void expandProvider() {
        addToExpandArgs(JSONKeys.ENTITY_PROVIDERS);
    }
}
