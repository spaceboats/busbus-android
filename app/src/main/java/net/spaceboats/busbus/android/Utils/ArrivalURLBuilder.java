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
    protected static final String ARRIVALS_STOP_ID = "stop.id";
    protected static final String ARRIVALS_ROUTE_ID = "route.id";
    protected static final String ARRIVALS_EXPAND = "_expand";
    protected static final String ARRIVALS_EXPAND_ROUTE = "routes";
    protected static final String ARRIVALS_EXPAND_STOP = "stops";
    protected static final String ARRIVALS_EXPAND_PROVIDER = "providers";
    protected static final String ARRIVALS_EXPAND_SEPARATOR = ",";
    private String expandArgs;

    public ArrivalURLBuilder(Context context) {
        super(context, ARRIVALS);
        expandArgs = "";
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
        addQueryParam(ARRIVALS_STOP_ID, id);
    }

    public void addRouteId(String id) {
        addQueryParam(ARRIVALS_ROUTE_ID, id);
    }

    public void expandRoute() {
        addToExpandArgs(ARRIVALS_EXPAND_ROUTE);
    }

    public void expandStop() {
        addToExpandArgs(ARRIVALS_EXPAND_STOP);
    }

    public void expandProvider() {
        addToExpandArgs(ARRIVALS_EXPAND_PROVIDER);
    }

    private void addToExpandArgs(String entityName) {
        expandArgs += entityName + ARRIVALS_EXPAND_SEPARATOR;
    }

    @Override
    public String getURL() {
        addQueryParam(ARRIVALS_EXPAND, expandArgs);
        return super.getURL();
    }
}
