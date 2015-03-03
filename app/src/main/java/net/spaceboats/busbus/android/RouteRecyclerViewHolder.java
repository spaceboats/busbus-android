package net.spaceboats.busbus.android;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zralston on 2/18/15.
 */
public class RouteRecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView mRouteNumberTextView;
    private TextView mRouteNameTextView;
    private CardView mBackgroundImageView;

    public RouteRecyclerViewHolder(View itemView) {
        super(itemView);
        mRouteNumberTextView = (TextView) itemView.findViewById(R.id.routeNumber);
        mBackgroundImageView = (CardView) itemView.findViewById(R.id.mbg);
        mRouteNameTextView = (TextView) itemView.findViewById(R.id.routeName);
    }

    public TextView getRouteNumberTextView() {
        return mRouteNumberTextView;
    }

    public TextView getRouteNameTextView(){
        return mRouteNumberTextView;
    }

    public void setRouteNumber(String number) {
        this.mRouteNumberTextView.setText(number);
    }

    public void setBackgroundColor(String color){
        this.mBackgroundImageView.setBackgroundColor(Color.parseColor(color));
    }

    public void setRouteName(String name){
        this.mRouteNameTextView.setText(name);
    }
}
