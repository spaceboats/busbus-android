package net.spaceboats.busbus.android;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zralston on 3/12/15.
 */
public class RouteViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView mRouteNumberTextView;
    private TextView mRouteNameTextView;
    private ImageView mRouteColorImageView;
    private Route mRoute;
    String routeColor = "#000000";

    public RouteViewHolder(View view) {
        super(view);
        mRouteNumberTextView = (TextView) view.findViewById(R.id.routeNumber);
        mRouteColorImageView = (ImageView) view.findViewById(R.id.mbg);
        mRouteNameTextView = (TextView) view.findViewById(R.id.routeName);
        rootView = view;
    }

    public void setData(Entity entity) {
        mRoute = (Route) entity;

        setRouteNumber("Route " + mRoute.getNumber());
        setBackgroundColor(mRoute.getColor());
        setRouteName(mRoute.getName());
    }

    public Route getRoute() {
        return mRoute;
    }

    public View getRootView() {
        return rootView;
    }

    private void setRouteNumber(String number) {
        this.mRouteNumberTextView.setText(number);
    }

    private void setBackgroundColor(String color){
        this.mRouteColorImageView.getDrawable().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_OVER);
        this.routeColor = color;
    }

    private void setRouteName(String name){
        this.mRouteNameTextView.setText(name);
    }
}
