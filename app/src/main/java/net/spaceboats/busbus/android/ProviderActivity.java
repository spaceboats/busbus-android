package net.spaceboats.busbus.android;

import android.os.Bundle;

import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Utils.ProviderURLBuilder;
import net.spaceboats.busbus.android.Utils.TransitDataIntentService;


/**
 * Created by Zane on 4/18/2015.
 */
public class ProviderActivity extends EntityBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra(getString(R.string.EXTRA_ENTITY_URL));
        String appBarTitle = getIntent().getStringExtra(getString(R.string.EXTRA_APP_BAR_TITLE));

        if(appBarTitle != null) {
            setTitle(appBarTitle);
        }

        if(url == null) {
            ProviderURLBuilder providerURLBuilder = new ProviderURLBuilder(getApplicationContext());
            url = providerURLBuilder.getURL();
        }

        // TODO: Change this to get providers
        TransitDataIntentService.startAction(this, url, TransitDataIntentService.ACTION_GET_ARRIVALS);
    }

    @Override
    public void entityClicked(Entity entity) {
        // TODO: Possibly display map of bus here
    }
}
