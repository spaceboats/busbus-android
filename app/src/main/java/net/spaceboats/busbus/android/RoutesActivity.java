package net.spaceboats.busbus.android;

import android.os.Bundle;

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

        mBlankEntityMessage = "No Routes";

        String url = getIntent().getStringExtra(getString(R.string.EXTRA_ENTITY_URL));

        if(url == null) {
            RouteURLBuilder routeURLBuilder = new RouteURLBuilder(getApplicationContext());
            url = routeURLBuilder.getURL();
        }

        TransitDataIntentService.startAction(this, url, TransitDataIntentService.ACTION_GET_ROUTES);
    }

    @Override
    public void entityClicked(Entity entity) {
        // It should be a route, but just check to be sure
        if(entity instanceof Route) {
            Route route = (Route) entity;
            ArrivalURLBuilder arrivalURLBuilder = new ArrivalURLBuilder(getApplicationContext());
            Date date = new Date();
            arrivalURLBuilder.addStartTime(Long.toString(date.getTime() / 1000));
            arrivalURLBuilder.addEndTime(Long.toString(date.getTime() / 1000 + 900));
            arrivalURLBuilder.addRouteId(route.getId());
            arrivalURLBuilder.addProviderId(route.getProviderId());
            arrivalURLBuilder.expandRoute();
            arrivalURLBuilder.expandStop();
            //arrivalURLBuilder.addLimit("1");
            switchToEntityActivity(arrivalURLBuilder.getURL(), "Route " + route.getShortName(), ArrivalsActivity.class);
        }
    }
}