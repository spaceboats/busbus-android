package net.spaceboats.busbus.android;

/**
 * Created by zralston on 3/2/15.
 */
public class GarbageRouteData {

    public static void setDefaultRouteData(RouteRecyclerAdapter routeAdapter) {
        // poor way to do this, but oh well.
        Route r1 = new Route("29", "27th & Wakarusa to KU", "#ff0000");
        Route r2 = new Route("42", "Campus Circulator", "#0000ff");
        Route r3 = new Route("30", "Bob Billings & Kasold to KU", "#00ff00");

        routeAdapter.addRoute(0, r1);
        routeAdapter.addRoute(1, r2);
        routeAdapter.addRoute(2, r3);
    }

    public static void setDefaultStopData(RouteRecyclerAdapter routeAdapter) {
        Route r1 = new Route("55", "Eaton Hall", "#ff0000");
        Route r2 = new Route("133", "Legends Place", "#00ff00");
        Route r3 = new Route("23", "Allen Fieldhouse", "#0000ff");
        Route r4 = new Route("47", "Hyvee", "#00ffff");

        routeAdapter.addRoute(0, r1);
        routeAdapter.addRoute(1, r2);
        routeAdapter.addRoute(2, r3);
        routeAdapter.addRoute(3, r4);
    }

}
