package net.spaceboats.busbus.android;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.spaceboats.busbus.android.DbHelper.EntityDbDelegator;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Route;
import net.spaceboats.busbus.android.Entites.Stop;
import net.spaceboats.busbus.android.RecyclerView.MyRecyclerAdapter;
import net.spaceboats.busbus.android.Utils.ArrivalURLBuilder;
import net.spaceboats.busbus.android.Utils.DataBroadcastReceiver;
import net.spaceboats.busbus.android.Utils.RouteURLBuilder;
import net.spaceboats.busbus.android.Utils.StopURLBuilder;
import net.spaceboats.busbus.android.Utils.TransitDataIntentService;

import java.util.Date;
import java.util.List;


public class ClosestStopActivity extends ActionBarActivity implements MyRecyclerAdapter.MyClickListener, DataBroadcastReceiver.IBroadcastReceiver {

    // TODO: Fix the fact that this class and RoutesActivity are pretty much identical with function calls

    private Toolbar toolbar;
    private DataBroadcastReceiver dataBroadcastReceiver;
    private RecyclerViewFragment recyclerViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        StopURLBuilder stopURLBuilder = new StopURLBuilder(getApplicationContext());
        stopURLBuilder.addLatitude(String.valueOf(getIntent().getDoubleExtra(getString(R.string.EXTRA_LOCATION_LATITUDE), 0)));
        stopURLBuilder.addLongitude(String.valueOf(getIntent().getDoubleExtra(getString(R.string.EXTRA_LOCATION_LONGITUDE), 0)));
        stopURLBuilder.addDistance("100");
        Log.v("TestURL", stopURLBuilder.getURL());
        TransitDataIntentService.startAction(this, stopURLBuilder.getURL(), TransitDataIntentService.ACTION_GET_STOPS);

        dataBroadcastReceiver = new DataBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter(TransitDataIntentService.ACTION_TRASIT_DATA_INTENT_SERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(dataBroadcastReceiver, intentFilter);
    }

    private void replaceRecyclerViewFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        recyclerViewFragment =
                RecyclerViewFragment.newinstance(getIntent().getIntExtra(getString(R.string.EXTRA_X_CLICKED_POSITION), 0),
                        getIntent().getIntExtra(getString(R.string.EXTRA_Y_CLICKED_POSITION), 0));
        transaction.replace(R.id.fragment_placeholder, recyclerViewFragment);
        transaction.commit();
    }

    private void switchToArrivalFragment(String url) {
        Log.v(getClass().getSimpleName(), url);
        replaceRecyclerViewFragment();
        TransitDataIntentService.startAction(this, url, TransitDataIntentService.ACTION_GET_ARRIVALS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_closest_stop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void entityClicked(Entity entity) {
        if(entity instanceof Stop) {
            setTitle(((Stop)entity).getStopName());
            ArrivalURLBuilder arrivalURLBuilder = new ArrivalURLBuilder(getApplicationContext());
            Date date = new Date();
            arrivalURLBuilder.addStartTime(Long.toString(date.getTime()/1000));
            arrivalURLBuilder.addEndTime(Long.toString(date.getTime() / 1000 + 900));
            arrivalURLBuilder.addStopId(((Stop)entity).getId());
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
