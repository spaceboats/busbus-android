package net.spaceboats.busbus.android;

import android.app.FragmentTransaction;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Stop;
import net.spaceboats.busbus.android.Utils.ArrivalURLBuilder;
import net.spaceboats.busbus.android.Utils.StopURLBuilder;
import net.spaceboats.busbus.android.Utils.TransitDataIntentService;

import java.util.Date;
import java.util.List;


public class ClosestStopActivity extends EntityBaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    Location mLocation;
    GoogleMap mMap;
    Marker lastClickedMarker;
    MapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Figure out why when the back button is pressed to this activity, it crashes.

        // TODO: Possibly check for a url coming in just like the other entity activities
        mLocation = new Location("My Location");
        mLocation.setLatitude(getIntent().getDoubleExtra(getString(R.string.EXTRA_LOCATION_LATITUDE), 0));
        mLocation.setLongitude(getIntent().getDoubleExtra(getString(R.string.EXTRA_LOCATION_LONGITUDE), 0));

        mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map_placeholder, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);

        StopURLBuilder stopURLBuilder = new StopURLBuilder(getApplicationContext());
        stopURLBuilder.addLatitude(String.valueOf(getIntent().getDoubleExtra(getString(R.string.EXTRA_LOCATION_LATITUDE), 0)));
        stopURLBuilder.addLongitude(String.valueOf(getIntent().getDoubleExtra(getString(R.string.EXTRA_LOCATION_LONGITUDE), 0)));
        stopURLBuilder.addDistance(String.valueOf(getIntent().getIntExtra(getString(R.string.EXTRA_LOCATION_DISTANCE), 100)));
        TransitDataIntentService.startAction(this, stopURLBuilder.getURL(), TransitDataIntentService.ACTION_GET_STOPS);
    }

    @Override
    public void entityClicked(Entity entity) {
        if(entity instanceof Stop) {
            ArrivalURLBuilder arrivalURLBuilder = new ArrivalURLBuilder(getApplicationContext());
            Date date = new Date();
            arrivalURLBuilder.addStartTime(Long.toString(date.getTime()/1000));
            arrivalURLBuilder.addEndTime(Long.toString(date.getTime()/1000 + 90));
            arrivalURLBuilder.addStopId(((Stop)entity).getId());
            arrivalURLBuilder.expandRoute();
            arrivalURLBuilder.expandStop();
            switchToEntityActivity(arrivalURLBuilder.getURL(), ((Stop)entity).getStopName(), ArrivalsActivity.class);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_closest_stop;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng closest = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());

        mMap = map;

        map.setMyLocationEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(closest, 17));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(closest)
                .zoom(17)
                .tilt(40)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void entityListReceived(List<Entity> entityList) {
        for(Entity entity : entityList) {
            Stop stop = (Stop) entity;
            LatLng location = new LatLng(stop.getLatitude(), stop.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .title(stop.getStopName())
                    .snippet(stop.getId())
                    .position(location));
        }

        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        boolean result = false;

        if(lastClickedMarker != null && marker.getId().equals(lastClickedMarker.getId())) {
            ArrivalURLBuilder arrivalURLBuilder = new ArrivalURLBuilder(getApplicationContext());
            Date date = new Date();
            arrivalURLBuilder.addStartTime(Long.toString(date.getTime() / 1000));
            arrivalURLBuilder.addEndTime(Long.toString(date.getTime() / 1000 + 900));
            arrivalURLBuilder.addStopId(marker.getSnippet());
            arrivalURLBuilder.expandRoute();
            arrivalURLBuilder.expandStop();
            //arrivalURLBuilder.addLimit("1");
            switchToEntityActivity(arrivalURLBuilder.getURL(), marker.getTitle(), ArrivalsActivity.class);
            result = true;
        }
        lastClickedMarker = marker;

        return result;
    }
}
