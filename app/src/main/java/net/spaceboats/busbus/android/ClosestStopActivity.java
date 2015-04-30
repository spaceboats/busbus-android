package net.spaceboats.busbus.android;

import android.os.Bundle;

import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Stop;
import net.spaceboats.busbus.android.Utils.ArrivalURLBuilder;
import net.spaceboats.busbus.android.Utils.StopURLBuilder;
import net.spaceboats.busbus.android.Utils.TransitDataIntentService;

import java.util.Date;


public class ClosestStopActivity extends EntityBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Possibly check for a url coming in just like the other entity activities

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
}
