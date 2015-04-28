package net.spaceboats.busbus.android.RecyclerView;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Route;
import net.spaceboats.busbus.android.R;
import net.spaceboats.busbus.android.RecyclerView.BaseViewHolder;

/**
 * Created by zralston on 3/12/15.
 */
class RouteViewHolder extends BaseViewHolder<Route> {

    private TextView mRouteNumberTextView;
    private TextView mRouteNameTextView;
    private ImageView mRouteColorImageView;

    public RouteViewHolder(View view) {
        super(view);
        mRouteNumberTextView = (TextView) view.findViewById(R.id.routeNumber);
        mRouteColorImageView = (ImageView) view.findViewById(R.id.mbg);
        mRouteNameTextView = (TextView) view.findViewById(R.id.routeName);
    }

    public boolean setData(Entity entity) {
        mEntity = (Route) entity;

        setRouteNumber("Route " + mEntity.getNumber());
        setRouteColor(mEntity.getColor());
        setRouteName(mEntity.getName());

        return true;
    }

    private void setRouteNumber(String number) {
        this.mRouteNumberTextView.setText(number);
    }

    private void setRouteColor(String color){
        this.mRouteColorImageView.getDrawable().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_OVER);
    }

    private void setRouteName(String name){
        this.mRouteNameTextView.setText(name);
    }
}
