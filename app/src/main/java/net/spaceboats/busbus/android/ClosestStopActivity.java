package net.spaceboats.busbus.android;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

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
        stopURLBuilder.addDistance("100");
        TransitDataIntentService.startAction(this, stopURLBuilder.getURL(), TransitDataIntentService.ACTION_GET_STOPS);
    }

    private void switchToArrivalActivity(String url, String appBarTitle) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(findViewById(R.id.app_bar), "app_bar"));
        Intent intent = new Intent(this, ArrivalsActivity.class);
        intent.putExtra(getString(R.string.EXTRA_ENTITY_URL), url);
        intent.putExtra(getString(R.string.EXTRA_APP_BAR_TITLE), appBarTitle);

        startActivity(intent, options.toBundle());
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
            switchToArrivalActivity(arrivalURLBuilder.getURL(), ((Stop)entity).getStopName());
        }
    }
}
