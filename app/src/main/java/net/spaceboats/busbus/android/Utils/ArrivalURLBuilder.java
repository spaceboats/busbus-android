package net.spaceboats.busbus.android.Utils;

import android.content.Context;

/**
 * Created by zralston on 4/1/15.
 */
public class ArrivalURLBuilder extends URLBuilder {

    public ArrivalURLBuilder(Context context) {
        super(context, JSONKeys.ENTITY_ARRIVALS);
    }

    public void addLimit(String amount) {
        addQueryParam(JSONKeys.ARRIVAL_LIMIT, amount);
    }

    public void addStartTime(String time) {
        addQueryParam(JSONKeys.ARRIVAL_START_TIME, time);
    }

    public void addEndTime(String time) {
        addQueryParam(JSONKeys.ARRIVAL_END_TIME, time);
    }

    public void addStopId(String id) {
        addEntityAttrParam(JSONKeys.STOP, JSONKeys.STOP_ID, id);
    }

    public void addRouteId(String id) {
        addEntityAttrParam(JSONKeys.ROUTE, JSONKeys.ROUTE_ID, id);
    }

    public void addProviderId(String id) {
        addEntityAttrParam(JSONKeys.PROVIDER, JSONKeys.PROVIDER_ID, id);
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
