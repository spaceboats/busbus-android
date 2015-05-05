package net.spaceboats.busbus.android;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import net.spaceboats.busbus.android.DbHelper.EntityDbDelegator;
import net.spaceboats.busbus.android.Entites.BlankEntity;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.RecyclerView.MyRecyclerAdapter;
import net.spaceboats.busbus.android.Utils.DataBroadcastReceiver;
import net.spaceboats.busbus.android.Utils.TransitDataIntentService;

import java.util.ArrayList;
import java.util.List;


public abstract class EntityBaseActivity extends ActionBarActivity implements MyRecyclerAdapter.MyClickListener, DataBroadcastReceiver.IBroadcastReceiver {

    private Toolbar toolbar;
    private DataBroadcastReceiver dataBroadcastReceiver;
    protected RecyclerViewFragment recyclerViewFragment;
    protected String mBlankEntityMessage = "No Data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

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

        String appBarTitle = getIntent().getStringExtra(getString(R.string.EXTRA_APP_BAR_TITLE));

        if(appBarTitle != null) {
            setTitle(appBarTitle);
        }
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
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(TransitDataIntentService.ACTION_TRANSIT_DATA_INTENT_SERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(dataBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(dataBroadcastReceiver);
    }

    private void replaceRecyclerViewFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        recyclerViewFragment =
                RecyclerViewFragment.newinstance(getIntent().getIntExtra(getString(R.string.EXTRA_X_CLICKED_POSITION), 0),
                        getIntent().getIntExtra(getString(R.string.EXTRA_Y_CLICKED_POSITION), 0));
        transaction.replace(getFragmentPlaceholderId(), recyclerViewFragment);
        transaction.commit();
    }

    protected int getLayoutResource() {
        return R.layout.activity_routes;
    }

    protected int getFragmentPlaceholderId() {
        return R.id.fragment_placeholder;
    }

    protected void switchToEntityActivity(String url, String appBarTitle, Class activityClass) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, findViewById(R.id.app_bar), "app_bar");
        Intent intent = new Intent(this, activityClass);
        intent.putExtra(getString(R.string.EXTRA_ENTITY_URL), url);
        intent.putExtra(getString(R.string.EXTRA_APP_BAR_TITLE), appBarTitle);

        startActivity(intent, options.toBundle());
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
    public void entityUnFavorited(final Entity entity, int position) {
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
        if(recyclerViewFragment != null) {
            if(entityList.size() == 0) {
                List<Entity> temp = new ArrayList<>();
                temp.add(new BlankEntity(mBlankEntityMessage));
                entityList = temp;
            }

            recyclerViewFragment.updateData(entityList);
        }
    }
}
