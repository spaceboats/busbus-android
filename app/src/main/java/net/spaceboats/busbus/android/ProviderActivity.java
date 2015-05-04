package net.spaceboats.busbus.android;

import android.os.Bundle;

import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Provider;
import net.spaceboats.busbus.android.Utils.ProviderURLBuilder;
import net.spaceboats.busbus.android.Utils.RouteURLBuilder;
import net.spaceboats.busbus.android.Utils.TransitDataIntentService;


/**
 * Created by Zane on 4/18/2015.
 */
public class ProviderActivity extends EntityBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra(getString(R.string.EXTRA_ENTITY_URL));

        if(url == null) {
            ProviderURLBuilder providerURLBuilder = new ProviderURLBuilder(getApplicationContext());
            url = providerURLBuilder.getURL();
        }

        TransitDataIntentService.startAction(this, url, TransitDataIntentService.ACTION_GET_PROVIDERS);
    }

    @Override
    public void entityClicked(Entity entity) {
        if(entity instanceof Provider) {
            Provider provider = (Provider) entity;
            RouteURLBuilder routeURLBuilder = new RouteURLBuilder(getApplicationContext());
            routeURLBuilder.addProviderId(provider.getProviderId());
            routeURLBuilder.expandProvider();
            switchToEntityActivity(routeURLBuilder.getURL(), provider.getCredit(), RoutesActivity.class);
        }
    }
}
