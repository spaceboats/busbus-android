package net.spaceboats.busbus.android;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;

import net.spaceboats.busbus.android.DbHelper.EntityDbDelegator;
import net.spaceboats.busbus.android.Entites.Arrival;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.RecyclerView.MyRecyclerAdapter;
import net.spaceboats.busbus.android.Utils.ArrivalURLBuilder;
import net.spaceboats.busbus.android.Utils.DataBroadcastReceiver;
import net.spaceboats.busbus.android.Utils.TransitDataIntentService;

import java.util.List;


public class FavoritesActivity extends ActionBarActivity implements MyRecyclerAdapter.MyClickListener, DataBroadcastReceiver.IBroadcastReceiver {

    private Toolbar toolbar;
    private RecyclerViewFragment recyclerViewFragment;
    private DataBroadcastReceiver dataBroadcastReceiver;

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

        dataBroadcastReceiver = new DataBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter(TransitDataIntentService.ACTION_TransitDataIntentService);
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

    @Override
    public void onResume() {
        super.onResume();

        List<Entity> favoriteArrivals = EntityDbDelegator.queryArrivals();

        // Used to figure out the next arrival time for each favorite
        for(Entity entity : favoriteArrivals) {
            Arrival arrival = (Arrival) entity;
            ArrivalURLBuilder arrivalURLBuilder = new ArrivalURLBuilder(getApplicationContext());
            arrivalURLBuilder.addRouteId(arrival.getRouteId());
            arrivalURLBuilder.addStopId(arrival.getStopId());
            arrivalURLBuilder.expandStop();
            arrivalURLBuilder.expandRoute();
            TransitDataIntentService.startActionGetRoutes(this, arrivalURLBuilder.getURL(), TransitDataIntentService.ACTION_GET_ARRIVALS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dataBroadcastReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
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

    }

    @Override
    public void entityFavorited(Entity entity) {

    }

    @Override
    public void entityUnFavorited(final Entity entity, int position) {
        entity.setFavorite(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                EntityDbDelegator.delete(entity);
            }
        }).start();

        recyclerViewFragment.removeEntity(position);
    }

    @Override
    public void entityListReceived(List<Entity> entityList) {
        // TODO: Change this later after I can query for the next arrival from busbus web.
        recyclerViewFragment.sortEntities(entityList);
        recyclerViewFragment.addEntity(entityList.get(0));
    }
}
