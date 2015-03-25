package net.spaceboats.busbus.android;

import net.spaceboats.busbus.android.Entites.Route;
import net.spaceboats.busbus.android.RecyclerView.MyRecyclerAdapter;

/**
 * Created by zralston on 3/2/15.
 */
public class GarbageRouteData {

    public static void setDefaultRouteData(MyRecyclerAdapter routeAdapter) {
        // poor way to do this, but oh well.
        Route r1 = new Route("29", "27th & Wakarusa to KU", "#ff0000", "1");
        Route r2 = new Route("42", "Campus Circulator", "#0000ff", "2");
        Route r3 = new Route("30", "Bob Billings & Kasold to KU", "#00ff00", "3");

        routeAdapter.addItem(0, r1);
        routeAdapter.addItem(1, r2);
        routeAdapter.addItem(2, r3);
    }

}
