package net.spaceboats.busbus.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;

public class ClosestStopActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RouteRecyclerAdapter mRouteAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DataBroadcastReceiver dataBroadcastReceiver;
    private final String LOG_TAG = ClosestStopActivity.this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closest_stop);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        String url = "http://api.openweathermap.org/data/2.5/weather?q=London,uk";
        TransitDataIntentService.startActionGetRoutes(this, url);

        dataBroadcastReceiver = new DataBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(TransitDataIntentService.ACTION_TransitDataIntentService);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(dataBroadcastReceiver, intentFilter);

        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        // Size will not change as the data changes in an item.
        mRecyclerView.setHasFixedSize(true);

        // Each item should appear one after another
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Route adapter that will handle adding and removing items.
        mRouteAdapter = new RouteRecyclerAdapter();
        mRecyclerView.setAdapter(mRouteAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dataBroadcastReceiver);
    }

    public class DataBroadcastReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra(TransitDataIntentService.EXTRA_KEY_OUT);
            Log.v("DataBroadcastReceiver", result);
            GarbageRouteData.setDefaultRouteData(mRouteAdapter);
        }
    }
}