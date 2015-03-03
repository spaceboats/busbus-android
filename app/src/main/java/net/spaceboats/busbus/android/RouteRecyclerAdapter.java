package net.spaceboats.busbus.android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zralston on 2/18/15.
 */
public class RouteRecyclerAdapter extends RecyclerView.Adapter<RouteRecyclerViewHolder> {

    private List<Route> mRoutes = new ArrayList<>();

    public RouteRecyclerAdapter() {

    }

    public void updateRoutes(List<Route> routes) {
        mRoutes = routes;
        notifyDataSetChanged();
    }

    public void addRoute(int position, Route route){
        mRoutes.add(position, route);
        notifyItemInserted(position);
    }

    public void removeRoute(int position){
        mRoutes.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mRoutes.size();
    }

    @Override
    public RouteRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View routeView = inflater.inflate(R.layout.route, viewGroup, false);
        return new RouteRecyclerViewHolder(routeView);
    }

    @Override
    public void onBindViewHolder(RouteRecyclerViewHolder viewHolder, int position) {
        Route item = mRoutes.get(position);
        viewHolder.setRouteNumber(item.getNumber());
        viewHolder.setBackgroundColor(item.getColor());
        viewHolder.setRouteName(item.getName());
    }
}
