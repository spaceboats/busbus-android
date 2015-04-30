package net.spaceboats.busbus.android;

import android.os.Bundle;

import net.spaceboats.busbus.android.DbHelper.EntityDbDelegator;
import net.spaceboats.busbus.android.Entites.Arrival;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Provider;
import net.spaceboats.busbus.android.Utils.ArrivalURLBuilder;
import net.spaceboats.busbus.android.Utils.ProviderURLBuilder;
import net.spaceboats.busbus.android.Utils.RouteURLBuilder;
import net.spaceboats.busbus.android.Utils.TransitDataIntentService;

import java.util.Date;
import java.util.List;


public class FavoritesActivity extends EntityBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        List<Entity> favorites = EntityDbDelegator.queryFavorites();

        // Used to figure out the next arrival time for each favorite
        for(Entity entity : favorites) {
            if(entity instanceof Provider) {
                // TODO: This is a hack, figure out why the providers will not show in the rcView, if I don't send a http request to the server.
                Provider provider = (Provider) entity;
                ProviderURLBuilder providerURLBuilder = new ProviderURLBuilder(getApplicationContext());
                providerURLBuilder.addId(provider.getId());
                TransitDataIntentService.startAction(this, providerURLBuilder.getURL(), TransitDataIntentService.ACTION_GET_PROVIDERS);
            }
            else if(entity instanceof Arrival) {
                Arrival arrival = (Arrival) entity;
                ArrivalURLBuilder arrivalURLBuilder = new ArrivalURLBuilder(getApplicationContext());
                arrivalURLBuilder.addRouteId(arrival.getRouteId());
                arrivalURLBuilder.addStopId(arrival.getStopId());
                Date date = new Date();
                arrivalURLBuilder.addStartTime(Long.toString(date.getTime() / 1000));
                arrivalURLBuilder.expandStop();
                arrivalURLBuilder.expandRoute();
                TransitDataIntentService.startAction(this, arrivalURLBuilder.getURL(), TransitDataIntentService.ACTION_GET_ARRIVALS);
            }
        }
    }

    @Override
    public void entityClicked(Entity entity) {
        if(entity instanceof Provider) {
            Provider provider = (Provider) entity;
            RouteURLBuilder routeURLBuilder = new RouteURLBuilder(getApplicationContext());
            routeURLBuilder.addProviderId(provider.getId());
            routeURLBuilder.expandProvider();
            switchToEntityActivity(routeURLBuilder.getURL(), provider.getCredit(), RoutesActivity.class);
        }
    }

    @Override
    public void entityFavorited(Entity entity) {
        // Do nothing for now
    }

    @Override
    public void entityUnFavorited(final Entity entity, int position) {
        super.entityUnFavorited(entity, position);

        // TODO: add an undo button for last deleted favorite
        recyclerViewFragment.removeEntity(position);
    }

    @Override
    public void entityListReceived(List<Entity> entityList) {
        // TODO: Change this later after I can query for the next arrival from busbus web.
        if (entityList.size() > 0) {
            if(entityList.get(0) instanceof Arrival) {
                recyclerViewFragment.sortEntities(entityList);
                recyclerViewFragment.addEntity(entityList.get(0));
            }
            else if(entityList.get(0) instanceof Provider) {
                for(Entity entity : entityList) {
                    recyclerViewFragment.addEntity(entity);
                }
            }
        }
    }
}
