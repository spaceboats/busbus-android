package net.spaceboats.busbus.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Stop;
import net.spaceboats.busbus.android.Utils.ArrivalURLBuilder;
import net.spaceboats.busbus.android.Utils.StopURLBuilder;
import net.spaceboats.busbus.android.Utils.TransitDataIntentService;

import java.util.Date;


public class ClosestStopActivity extends EntityBaseActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Possibly check for a url coming in just like the other entity activities
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        StopURLBuilder stopURLBuilder = new StopURLBuilder(getApplicationContext());
        stopURLBuilder.addLatitude(String.valueOf(getIntent().getDoubleExtra(getString(R.string.EXTRA_LOCATION_LATITUDE), 0)));
        stopURLBuilder.addLongitude(String.valueOf(getIntent().getDoubleExtra(getString(R.string.EXTRA_LOCATION_LONGITUDE), 0)));
        stopURLBuilder.addDistance(String.valueOf(getIntent().getIntExtra(getString(R.string.EXTRA_LOCATION_DISTANCE), 100)));
        TransitDataIntentService.startAction(this, stopURLBuilder.getURL(), TransitDataIntentService.ACTION_GET_STOPS);
    }

    @Override
    public void entityClicked(Entity entity) {
        // It should be a Stop, but just check to be sure
        if(entity instanceof Stop) {
            ArrivalURLBuilder arrivalURLBuilder = new ArrivalURLBuilder(getApplicationContext());
            Date date = new Date();
            arrivalURLBuilder.addStartTime(Long.toString(date.getTime()/1000));
            arrivalURLBuilder.addEndTime(Long.toString(date.getTime() / 1000 + 900));
            arrivalURLBuilder.addStopId(((Stop)entity).getId());
            arrivalURLBuilder.expandRoute();
            arrivalURLBuilder.expandStop();
            //arrivalURLBuilder.addLimit("1");
            switchToEntityActivity(arrivalURLBuilder.getURL(), ((Stop)entity).getStopName(), ArrivalsActivity.class);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_closest_stop;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng closest = new LatLng(-33.867, 151.206);

        Log.v(getClass().getName(), "ON MAP READY");
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(closest, 10));

        map.addMarker(new MarkerOptions()
                .title("Closest")
                .snippet("test")
                .position(closest));
    }
}
