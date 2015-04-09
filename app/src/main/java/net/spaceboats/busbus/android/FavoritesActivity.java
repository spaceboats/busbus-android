package net.spaceboats.busbus.android;

import android.os.Bundle;

import net.spaceboats.busbus.android.DbHelper.EntityDbDelegator;
import net.spaceboats.busbus.android.Entites.Arrival;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Utils.ArrivalURLBuilder;
import net.spaceboats.busbus.android.Utils.TransitDataIntentService;

import java.util.List;


public class FavoritesActivity extends EntityBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        List<Entity> favoriteArrivals = EntityDbDelegator.queryArrivals();

        // Used to figure out the next arrival time for each favorite
        for(Entity entity : favoriteArrivals) {
            Arrival arrival = (Arrival) entity;
            ArrivalURLBuilder arrivalURLBuilder = new ArrivalURLBuilder(getApplicationContext());
            arrivalURLBuilder.addRouteId(arrival.getRouteId());
            arrivalURLBuilder.addStopId(arrival.getStopId());
            arrivalURLBuilder.expandStop();
            arrivalURLBuilder.expandRoute();
            TransitDataIntentService.startAction(this, arrivalURLBuilder.getURL(), TransitDataIntentService.ACTION_GET_ARRIVALS);
        }
    }

    @Override
    public void entityClicked(Entity entity) {
        // Do nothing for now
    }

    @Override
    public void entityFavorited(Entity entity) {
        // Do nothing for now
    }

    @Override
    public void entityUnFavorited(final Entity entity, int position) {
        super.entityUnFavorited(entity, position);

        recyclerViewFragment.removeEntity(position);
    }

    @Override
    public void entityListReceived(List<Entity> entityList) {
        // TODO: Change this later after I can query for the next arrival from busbus web.
        if(entityList.size() > 0) {
            recyclerViewFragment.sortEntities(entityList);
            recyclerViewFragment.addEntity(entityList.get(0));
        }
    }
}
