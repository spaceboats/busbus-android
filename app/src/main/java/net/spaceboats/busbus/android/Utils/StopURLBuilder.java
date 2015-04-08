package net.spaceboats.busbus.android.Utils;

import android.content.Context;

/**
 * Created by Zane on 4/2/2015.
 */
public class StopURLBuilder extends URLBuilder {

    public StopURLBuilder(Context context) {
        super(context, JSONKeys.ENTITY_STOPS);
    }

    public void addLatitude(String latitude) {
        addQueryParam(JSONKeys.STOP_LATITUDE, latitude);
    }

    public void addLongitude(String longitude) {
        addQueryParam(JSONKeys.STOP_LONGITUDE, longitude);
    }

    public void addDistance(String distance) {
        addToPath(JSONKeys.STOP_DISTANCE_PATH_EXTENSION);
        addQueryParam(JSONKeys.STOP_DISTANCE, distance);
    }
}
