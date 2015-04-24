package net.spaceboats.busbus.android;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;

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
        String appBarTitle = getIntent().getStringExtra(getString(R.string.EXTRA_APP_BAR_TITLE));

        if(appBarTitle != null) {
            setTitle(appBarTitle);
        }

        if(url == null) {
            ProviderURLBuilder providerURLBuilder = new ProviderURLBuilder(getApplicationContext());
            url = providerURLBuilder.getURL();
        }

        TransitDataIntentService.startAction(this, url, TransitDataIntentService.ACTION_GET_PROVIDERS);
    }

    private void switchToRouteActivity(String url, String appBarTitle) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, findViewById(R.id.app_bar), "app_bar");
        Intent intent = new Intent(this, RoutesActivity.class);
        intent.putExtra(getString(R.string.EXTRA_ENTITY_URL), url);
        intent.putExtra(getString(R.string.EXTRA_APP_BAR_TITLE), appBarTitle);

        startActivity(intent, options.toBundle());
    }

    @Override
    public void entityClicked(Entity entity) {
        if(entity instanceof Provider) {
            Provider provider = (Provider) entity;
            RouteURLBuilder routeURLBuilder = new RouteURLBuilder(getApplicationContext());
            routeURLBuilder.addProviderId(provider.getId());
            routeURLBuilder.expandProvider();
            switchToRouteActivity(routeURLBuilder.getURL(), provider.getCredit());
        }
    }
}
