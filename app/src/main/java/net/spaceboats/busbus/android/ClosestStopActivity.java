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
    private Class mMyClass;

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

        mMyClass = Route.class;
        URLBuilder urlBuilder = new URLBuilder(getApplicationContext(), URLBuilder.ROUTES);
        Log.v("TestURL", urlBuilder.getURL());
        TransitDataIntentService.startActionGetRoutes(this, urlBuilder.getURL(), TransitDataIntentService.ACTION_GET_ROUTES);

        dataBroadcastReceiver = new DataBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(TransitDataIntentService.ACTION_TransitDataIntentService);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(dataBroadcastReceiver, intentFilter);
    }

    public void switchFragment(String url, Class myClass) {
        mMyClass = myClass;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        recyclerViewFragment =
                RecyclerViewFragment.newinstance(getIntent().getIntExtra(getString(R.string.EXTRA_X_CLICKED_POSITION), 0),
                        getIntent().getIntExtra(getString(R.string.EXTRA_Y_CLICKED_POSITION), 0));
        transaction.replace(R.id.fragment_placeholder, recyclerViewFragment);
        transaction.commit();

        if(myClass == Arrival.class)
            TransitDataIntentService.startActionGetRoutes(this, url, TransitDataIntentService.ACTION_GET_ARRIVALS);
        else if(myClass == Stop.class)
            TransitDataIntentService.startActionGetRoutes(this, url, TransitDataIntentService.ACTION_GET_STOPS);
        else if(myClass == Route.class)
            TransitDataIntentService.startActionGetRoutes(this, url, TransitDataIntentService.ACTION_GET_ROUTES);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dataBroadcastReceiver);
    }

    public class DataBroadcastReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra(TransitDataIntentService.EXTRA_KEY_OUT);
            if(result != null && recyclerViewFragment != null) {
                MyRecyclerAdapter mRouteAdapter = recyclerViewFragment.getRouteAdapter();
                //GarbageRouteData.setDefaultRouteData(mRouteAdapter);
                Log.v("DataBroadcastReceiver", result);
                try {
                    if(mMyClass == Arrival.class)
                        TheJSONParser.addArrivals(mRouteAdapter, result);
                    else if(mMyClass == Stop.class)
                        TheJSONParser.addStops(mRouteAdapter, result);
                    else if(mMyClass == Route.class)
                        TheJSONParser.addRoutes(mRouteAdapter, result);
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
