package net.spaceboats.busbus.android.Utils;

import android.content.Context;

/**
 * Created by Zane on 4/2/2015.
 */
public class RouteURLBuilder extends URLBuilder {

    public RouteURLBuilder(Context context) {
        super(context, JSONKeys.ENTITY_ROUTES);
    }

    public void addProviderId(String id) {
        addEntityAttrParam(JSONKeys.PROVIDER, JSONKeys.PROVIDER_ID, id);
    }

    public void expandProvider() {
        addToExpandArgs(JSONKeys.ENTITY_PROVIDERS);
    }
}
