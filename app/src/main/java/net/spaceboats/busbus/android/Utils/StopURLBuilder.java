package net.spaceboats.busbus.android.Utils;

import android.content.Context;

/**
 * Created by Zane on 4/2/2015.
 */
public class StopURLBuilder extends URLBuilder {

    public StopURLBuilder(Context context) {
        super(context, JSONKeys.ENTITY_STOPS);
    }
}
