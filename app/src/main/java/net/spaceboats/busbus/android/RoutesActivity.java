package net.spaceboats.busbus.android;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Route;
import net.spaceboats.busbus.android.Utils.ArrivalURLBuilder;
import net.spaceboats.busbus.android.Utils.RouteURLBuilder;
import net.spaceboats.busbus.android.Utils.TransitDataIntentService;

import java.util.Date;


public class RoutesActivity extends EntityBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra(getString(R.string.EXTRA_ENTITY_URL));

        if(url == null) {
            RouteURLBuilder routeURLBuilder = new RouteURLBuilder(getApplicationContext());
            url = routeURLBuilder.getURL();
        }

        TransitDataIntentService.startAction(this, url, TransitDataIntentService.ACTION_GET_ROUTES);
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
        // It should be a route, but just check to be sure
        if(entity instanceof Route) {
            ArrivalURLBuilder arrivalURLBuilder = new ArrivalURLBuilder(getApplicationContext());
            Date date = new Date();
            arrivalURLBuilder.addStartTime(Long.toString(date.getTime()/1000));
            arrivalURLBuilder.addEndTime(Long.toString(date.getTime() / 1000 + 900));
            arrivalURLBuilder.addRouteId("RT_" + ((Route) entity).getNumber());
            arrivalURLBuilder.expandRoute();
            arrivalURLBuilder.expandStop();
            //arrivalURLBuilder.addLimit("1");
            switchToArrivalActivity(arrivalURLBuilder.getURL(), "Route " + ((Route)entity).getNumber());
        }
    }
}
