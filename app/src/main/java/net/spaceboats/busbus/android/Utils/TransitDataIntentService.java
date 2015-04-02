package net.spaceboats.busbus.android.Utils;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Gets transit data from the server
 */
public class TransitDataIntentService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_GET_ROUTES = "net.spaceboats.busbus.android.action.GET_ROUTES";
    public static final String ACTION_GET_ARRIVALS = "net.spaceboats.busbus.android.action.GET_ARRIVALS";
    public static final String ACTION_GET_STOPS = "net.spaceboats.busbus.android.action.GET_STOPS";
    public static final String ACTION_TransitDataIntentService = "net.spaceboats.busbus.androidintentservice.RESPONSE";
    private static final String EXTRA_URL = "net.spaceboats.busbus.android.extra.URL";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    public static final String EXTRA_ENTITY_TYPE_OUT = "EXTRA_ENTITY_TYPE";
    public static final String ENTITY_TYPE_ARRIVALS = "ARRIVALS";
    public static final String ENTITY_TYPE_ROUTES = "ROUTES";
    public static final String ENTITY_TYPE_STOPS = "STOPS";
    private String ENTITY_TYPE;

    /**
     * Creates a Service to send Http request and broadcast response
     */
    public static void startActionGetRoutes(Context context, String param1, String action) {
        Intent intent = new Intent(context, TransitDataIntentService.class);
        intent.setAction(action);
        intent.putExtra(EXTRA_URL, param1);
        context.startService(intent);
    }

    public TransitDataIntentService() {
        super("TransitDataIntentService");
    }

    /**
     * Calls the corresponding handleAction
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ROUTES.equals(action)) {
                ENTITY_TYPE = ENTITY_TYPE_ROUTES;
                final String param1 = intent.getStringExtra(EXTRA_URL);
                handleActionGetRoutes(param1);
            }
            else if (ACTION_GET_ARRIVALS.equals(action)) {
                ENTITY_TYPE = ENTITY_TYPE_ARRIVALS;
                final String param1 = intent.getStringExtra(EXTRA_URL);
                handleActionGetRoutes(param1);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetRoutes(String url) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        String responseData = null;
        try {
            URL urlObj = new URL(url);

            httpURLConnection = (HttpURLConnection) urlObj.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if(inputStream == null) {
                responseData = null;
            }
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if(buffer.length() == 0) {
                responseData = null;
            }
            responseData = buffer.toString();

        } catch(IOException e) {
            Log.e("TransitDataService", "Error",e);
        } finally {
            if(httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (final IOException e) {
                    Log.e("TransitDataService", "Error", e);
                }
            }
        }

        Intent intentResponse = new Intent();
        intentResponse.setAction(ACTION_TransitDataIntentService);
        intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
        intentResponse.putExtra(EXTRA_KEY_OUT, responseData);
        intentResponse.putExtra(EXTRA_ENTITY_TYPE_OUT, ENTITY_TYPE);
        sendBroadcast(intentResponse);
    }
}