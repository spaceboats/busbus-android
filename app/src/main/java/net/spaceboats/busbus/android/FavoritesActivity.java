package net.spaceboats.busbus.android;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;

import net.spaceboats.busbus.android.DbHelper.ArrivalDbOperator;
import net.spaceboats.busbus.android.DbHelper.FavoritesContract;
import net.spaceboats.busbus.android.DbHelper.RouteDbOperator;
import net.spaceboats.busbus.android.DbHelper.StopDbOperator;

import java.util.List;


public class FavoritesActivity extends ActionBarActivity implements RecyclerViewFragment.PassBackData {

    private Toolbar toolbar;
    private RecyclerViewFragment recyclerViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }

    @Override
    public void onResume() {
        super.onResume();
        RouteDbOperator routeDbOperator = new RouteDbOperator(getApplicationContext());
        StopDbOperator stopDbOperator = new StopDbOperator(getApplicationContext());
        ArrivalDbOperator arrivalDbOperator = new ArrivalDbOperator(getApplicationContext());
        List<Entity> entityList = routeDbOperator.query();
        //entityList.addAll(stopDbOperator.query());
        //entityList.addAll(arrivalDbOperator.query());
        recyclerViewFragment.updateData(entityList);
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
    public void itemClicked(Entity entity) {

    }
}
