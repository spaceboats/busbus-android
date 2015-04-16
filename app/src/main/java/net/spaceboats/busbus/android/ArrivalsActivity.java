package net.spaceboats.busbus.android;

import android.os.Bundle;
import android.util.Log;

import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Utils.ArrivalURLBuilder;
import net.spaceboats.busbus.android.Utils.TransitDataIntentService;


public class ArrivalsActivity extends EntityBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra(getString(R.string.EXTRA_ENTITY_URL));
        String appBarTitle = getIntent().getStringExtra(getString(R.string.EXTRA_APP_BAR_TITLE));

        if(appBarTitle != null) {
            setTitle(appBarTitle);
        }

        if(url == null) {
            ArrivalURLBuilder arrivalURLBuilder = new ArrivalURLBuilder(getApplicationContext());
            url = arrivalURLBuilder.getURL();
        }

        TransitDataIntentService.startAction(this, url, TransitDataIntentService.ACTION_GET_ARRIVALS);
    }

    @Override
    public void entityClicked(Entity entity) {
        // TODO: Possibly display map of bus here
    }

}
