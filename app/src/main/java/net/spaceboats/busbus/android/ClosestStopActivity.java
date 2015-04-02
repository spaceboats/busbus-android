package net.spaceboats.busbus.android;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;

import net.spaceboats.busbus.android.DbHelper.EntityDbDelegator;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Route;
import net.spaceboats.busbus.android.RecyclerView.MyRecyclerAdapter;
import net.spaceboats.busbus.android.Utils.ArrivalURLBuilder;
import net.spaceboats.busbus.android.Utils.DataBroadcastReceiver;
import net.spaceboats.busbus.android.Utils.RouteURLBuilder;
import net.spaceboats.busbus.android.Utils.TransitDataIntentService;

import java.util.Date;
import java.util.List;

public class ClosestStopActivity extends ActionBarActivity implements MyRecyclerAdapter.MyClickListener, DataBroadcastReceiver.IBroadcastReceiver {

    private Toolbar toolbar;
    private DataBroadcastReceiver dataBroadcastReceiver;
    private RecyclerViewFragment recyclerViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closest_stop);

        EntityDbDelegator.initDbDelegator(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);

        if (savedInstanceState == null) {
            replaceRecyclerViewFragment();
        }

        RouteURLBuilder routeURLBuilder = new RouteURLBuilder(getApplicationContext());
        Log.v("TestURL", routeURLBuilder.getURL());
        TransitDataIntentService.startAction(this, routeURLBuilder.getURL(), TransitDataIntentService.ACTION_GET_ROUTES);

        dataBroadcastReceiver = new DataBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter(TransitDataIntentService.ACTION_TRASIT_DATA_INTENT_SERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(dataBroadcastReceiver, intentFilter);
    }

    private void switchToArrivalFragment(String url) {
        Log.v(getClass().getSimpleName(), url);
        replaceRecyclerViewFragment();
        TransitDataIntentService.startAction(this, url, TransitDataIntentService.ACTION_GET_ARRIVALS);
    }

    private void switchToStopFragment(String url) {
        Log.v(getClass().getSimpleName(), url);
        replaceRecyclerViewFragment();
        TransitDataIntentService.startAction(this, url, TransitDataIntentService.ACTION_GET_STOPS);
    }

    private void switchToRouteFragment(String url) {
        Log.v(getClass().getSimpleName(), url);
        replaceRecyclerViewFragment();
        TransitDataIntentService.startAction(this, url, TransitDataIntentService.ACTION_GET_ROUTES);
    }

    private void replaceRecyclerViewFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        recyclerViewFragment =
                RecyclerViewFragment.newinstance(getIntent().getIntExtra(getString(R.string.EXTRA_X_CLICKED_POSITION), 0),
                        getIntent().getIntExtra(getString(R.string.EXTRA_Y_CLICKED_POSITION), 0));
        transaction.replace(R.id.fragment_placeholder, recyclerViewFragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dataBroadcastReceiver);
    }

    @Override
    public void entityClicked(Entity entity) {
        if(entity instanceof Route) {
            setTitle("Route " + ((Route)entity).getNumber());
            ArrivalURLBuilder arrivalURLBuilder = new ArrivalURLBuilder(getApplicationContext());
            Date date = new Date();
            arrivalURLBuilder.addStartTime(Long.toString(date.getTime()/1000));
            arrivalURLBuilder.addEndTime(Long.toString(date.getTime() / 1000 + 900));
            arrivalURLBuilder.addRouteId("RT_" + ((Route) entity).getNumber());
            arrivalURLBuilder.expandRoute();
            arrivalURLBuilder.expandStop();
            //arrivalURLBuilder.addLimit("1");
            switchToArrivalFragment(arrivalURLBuilder.getURL());
        }
    }

    @Override
    public void entityFavorited(final Entity entity) {
        entity.setFavorite(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                EntityDbDelegator.insert(entity);
            }
        }).start();
    }

    @Override
    public void entityUnFavorited(final Entity entity, int postion) {
        entity.setFavorite(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                EntityDbDelegator.delete(entity);
            }
        }).start();
    }

    @Override
    public void entityListReceived(List<Entity> entityList) {
        recyclerViewFragment.updateData(entityList);
    }
}
