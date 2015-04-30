package net.spaceboats.busbus.android;

import android.app.ActivityOptions;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import net.spaceboats.busbus.android.DbHelper.EntityDbDelegator;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener{

    private Toolbar toolbar;
    private int clicked_x_coord = 0;
    private int clicked_y_coord = 0;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;

    @Override
    public void onConnectionSuspended(int x) {
        Log.v(getClass().getName(), "Connection Suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v(getClass().getName(), "Connection Failed");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ConnectionResult.SUCCESS == GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)) {
            buildGoogleApiClient();
        }
        setContentView(R.layout.activity_main);

        EntityDbDelegator.initDbDelegator(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);


        final CardView cardView = (CardView) findViewById(R.id.providersCardView);

        cardView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    clicked_x_coord = (int) event.getX();
                    clicked_y_coord = (int) event.getY();
                }

                // return false so click events are not blocked
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void launchFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        intent.putExtra(getString(R.string.EXTRA_X_CLICKED_POSITION), clicked_x_coord);
        intent.putExtra(getString(R.string.EXTRA_Y_CLICKED_POSITION), clicked_y_coord);

        startActivity(intent, getActivityOptions().toBundle());
    }

    public void launchRoutes(View view) {
        Intent intent = new Intent(this, RoutesActivity.class);
        intent.putExtra(getString(R.string.EXTRA_X_CLICKED_POSITION), clicked_x_coord);
        intent.putExtra(getString(R.string.EXTRA_Y_CLICKED_POSITION), clicked_y_coord);

        startActivity(intent, getActivityOptions().toBundle());
    }

    public void launchClosestStops(View view) {
        if(mLocation != null) {
            // geo fix -95.253840 38.957155
            Intent intent = new Intent(this, ClosestStopActivity.class);
            intent.putExtra(getString(R.string.EXTRA_LOCATION_LATITUDE), mLocation.getLatitude());
            intent.putExtra(getString(R.string.EXTRA_LOCATION_LONGITUDE), mLocation.getLongitude());
            intent.putExtra(getString(R.string.EXTRA_LOCATION_DISTANCE), 100);

            startActivity(intent, getActivityOptions().toBundle());
        }
        else {
            Toast.makeText(getApplicationContext(), "Failed to get location data", Toast.LENGTH_SHORT).show();
        }
    }

    public void launchProviders(View view) {
        Intent intent = new Intent(this, ProviderActivity.class);

        startActivity(intent, getActivityOptions().toBundle());
    }

    private ActivityOptions getActivityOptions() {
        return ActivityOptions.makeSceneTransitionAnimation(this, findViewById(R.id.app_bar), "app_bar");
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setNumUpdates(1);
        locationRequest.setInterval(1000);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v(getClass().getName() + "/Latitude", String.valueOf(location.getLatitude()));
        Log.v(getClass().getName() + "/Longitude", String.valueOf(location.getLongitude()));
        mLocation = location;
    }
}
