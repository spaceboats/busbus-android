package net.spaceboats.busbus.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class ClosestStopActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private DataBroadcastReceiver dataBroadcastReceiver;
    private RecyclerViewFragment recyclerViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closest_stop);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            recyclerViewFragment =
                    RecyclerViewFragment.newinstance(getIntent().getIntExtra(getString(R.string.EXTRA_X_CLICKED_POSITION), 0),
                                                     getIntent().getIntExtra(getString(R.string.EXTRA_Y_CLICKED_POSITION), 0));
            transaction.replace(R.id.fragment_placeholder, recyclerViewFragment);
            transaction.commit();
        }

        //String url = "http://api.openweathermap.org/data/2.5/weather?q=London,uk";
        final String SCHEME = "http";
        final String AUTHORITY = "ec2-54-68-11-133.us-west-2.compute.amazonaws.com";
        final String ROUTES = "routes";
        final String STOPS = "stops";
        final String ARRIVALS = "arrivals";

        try {
            Uri.Builder builder = new Uri.Builder();
            Uri builtUri = builder.scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(STOPS).build();

            Log.v("URL", builtUri.toString());

            URL url = new URL(builtUri.toString());

            TransitDataIntentService.startActionGetRoutes(this, url.toString());
        } catch (MalformedURLException ue) {
            Log.v("URL Error", "Could not generate URL");
            Toast.makeText(getApplicationContext(), "Couldn't retrieve data",
                    Toast.LENGTH_LONG).show();
        }

        dataBroadcastReceiver = new DataBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(TransitDataIntentService.ACTION_TransitDataIntentService);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(dataBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dataBroadcastReceiver);
    }

    public class DataBroadcastReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra(TransitDataIntentService.EXTRA_KEY_OUT);
            if(result != null) {
                MyRecyclerAdapter mRouteAdapter = recyclerViewFragment.getRouteAdapter();
                //GarbageRouteData.setDefaultRouteData(mRouteAdapter);
                Log.v("DataBroadcastReceiver", result);
                try {
                    TheJSONParser.addStops(mRouteAdapter, result);
                } catch (JSONException je) {
                    Log.v("JSON Error", "Could not parse JSON");
                    Toast.makeText(getApplicationContext(), "Could not get route data",
                            Toast.LENGTH_LONG).show();
                }
            }
            else
                Log.v("DataBroadcastReceiver", "Received null message");
        }
    }
}
