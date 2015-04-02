package net.spaceboats.busbus.android.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import net.spaceboats.busbus.android.Entites.Entity;

import org.json.JSONException;

import java.util.List;

/**
 * Created by zralston on 4/2/15.
 */
public class DataBroadcastReceiver extends BroadcastReceiver {

    public interface IBroadcastReceiver {
        public void entityListReceived(List<Entity> entityList);
    }

    private IBroadcastReceiver mReceiver;

    public DataBroadcastReceiver(IBroadcastReceiver receiver) {
        mReceiver = receiver;
    }

    public void onReceive(Context context, Intent intent) {
        String result = intent.getStringExtra(TransitDataIntentService.EXTRA_KEY_OUT);
        String entityType = intent.getStringExtra(TransitDataIntentService.EXTRA_ENTITY_TYPE_OUT);
        if(result != null) {
            Log.v("DataBroadcastReceiver", result);
            try {
                switch (entityType) {
                    case TransitDataIntentService.ENTITY_TYPE_ARRIVALS:
                        mReceiver.entityListReceived(TheJSONParser.getArrivalList(result));
                        break;
                    case TransitDataIntentService.ENTITY_TYPE_ROUTES:
                        mReceiver.entityListReceived(TheJSONParser.getRouteList(result));
                        break;
                    case TransitDataIntentService.ENTITY_TYPE_STOPS:
                        mReceiver.entityListReceived(TheJSONParser.getStopList(result));
                        break;
                }
            } catch (JSONException je) {
                Log.v("JSON Error", "Could not parse JSON");
            }
        }
        else
            Log.v("DataBroadcastReceiver", "Received null message");
    }
}
