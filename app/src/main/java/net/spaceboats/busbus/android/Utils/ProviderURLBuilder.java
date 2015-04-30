package net.spaceboats.busbus.android.Utils;

import android.content.Context;

/**
 * Created by Zane on 4/2/2015.
 */
public class ProviderURLBuilder extends URLBuilder {

    public ProviderURLBuilder(Context context) {
        super(context, JSONKeys.ENTITY_PROVIDERS);
    }

    public void addId(String id) {
        addQueryParam(JSONKeys.PROVIDER_ID, id);
    }
}
