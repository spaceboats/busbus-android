package net.spaceboats.busbus.android;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by zralston on 2/18/15.
 */
public class MyRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mRouteNumberTextView;
    private TextView mRouteNameTextView;
    private TextView mStopNameTextView;
    private TextView mNextArrivalTime;
    private ImageView mRouteColorImageView;
    private LinearLayout mCardviewLinearLayout;
    Entity mEntity;
    String routeColor = "#000000";

    public MyRecyclerViewHolder(View itemView) {
        super(itemView);
        mRouteNumberTextView = (TextView) itemView.findViewById(R.id.routeNumber);
        mRouteColorImageView = (ImageView) itemView.findViewById(R.id.mbg);
        mRouteNameTextView = (TextView) itemView.findViewById(R.id.routeName);
        mStopNameTextView = (TextView) itemView.findViewById(R.id.stopName);
        mNextArrivalTime = (TextView) itemView.findViewById(R.id.nextArrivalTime);
        mCardviewLinearLayout = (LinearLayout) itemView.findViewById(R.id.cardviewLinearLayout);

        itemView.setOnClickListener(this);
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
        this.mRouteColorImageView.getDrawable().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_OVER);
        this.routeColor = color;
    }

    public void setRouteName(String name){
        this.mRouteNameTextView.setText(name);
    }

    public void setStopName(String name) {
        this.mStopNameTextView.setText(name);
    }

    public void setNextArrivalTime(String time) {
        this.mNextArrivalTime.setText(time);
    }


    private void toggleStopItems(int visibility) {
        //setOrientation(LinearLayout.HORIZONTAL);

        mStopNameTextView.setVisibility(visibility);
    }

    private void toggleRouteItems(int visibility) {
        //setOrientation(LinearLayout.VERTICAL);

        mRouteColorImageView.setVisibility(visibility);
        mRouteNameTextView.setVisibility(visibility);
        mRouteNumberTextView.setVisibility(visibility);
    }

    private void toggleArrival(int visibility) {
        //setOrientation(LinearLayout.HORIZONTAL);

        mNextArrivalTime.setVisibility(visibility);
        mStopNameTextView.setVisibility(visibility);
    }

    private void setOrientation(int orientation) {
        mCardviewLinearLayout.setOrientation(orientation);
    }

    public void setData(Entity entity) {
        mEntity = entity;
        if(Route.class.isInstance(entity)) {
            Route route = (Route) entity;

            setRouteNumber("Route " + route.getNumber());
            setBackgroundColor(route.getColor());
            setRouteName(route.getName());

            // Always call the ones with View.Gone first
            toggleArrival(View.GONE);
            toggleStopItems(View.GONE);
            toggleRouteItems(View.VISIBLE);
        }
        else if(Stop.class.isInstance(entity)) {
            Stop stop = (Stop) entity;
            setStopName(stop.getStopName());

            // Always call the ones with View.Gone first
            toggleRouteItems(View.GONE);
            toggleArrival(View.GONE);
            toggleStopItems(View.VISIBLE);
        }
        else if(Arrival.class.isInstance(entity)) {
            Arrival arrival = (Arrival) entity;
            setStopName(arrival.getStop().getStopName());
            Date date = new Date();
            setNextArrivalTime(arrival.getStringOfTimeDifferenceInMinutes(date.getTime()/1000) + " mins");

            // Always call the ones with View.Gone first
            toggleRouteItems(View.GONE);
            toggleStopItems(View.GONE);
            toggleArrival(View.VISIBLE);
        }
    }

    public void onClick(View view) {
        if(Route.class.isInstance(mEntity)) {
            URLBuilder urlBuilder = new URLBuilder(view.getContext(), URLBuilder.ARRIVALS);
            //urlBuilder.addQueryParam("start_time", "1426075200");
            Date date = new Date();
            urlBuilder.addQueryParam("end_time", Long.toString(date.getTime()/1000 + 1800));
            urlBuilder.addQueryParam("route.id", "RT_" + ((Route) mEntity).getNumber());
            urlBuilder.addQueryParam("_expand", "routes,stops");
            ((ClosestStopActivity) view.getContext()).switchFragment(urlBuilder.getURL(), Arrival.class);
        }
        else if(Arrival.class.isInstance(mEntity)) {
            /*
            URLBuilder urlBuilder = new URLBuilder(view.getContext(), URLBuilder.ARRIVALS);
            urlBuilder.addQueryParam("start_time", "1426075200");
            urlBuilder.addQueryParam("route.short_name", ((Arrival) mEntity).getRoute().getNumber());
            urlBuilder.addQueryParam("_expand", "routes,stops");
            ((ClosestStopActivity) view.getContext()).switchFragment(urlBuilder.getURL(), Arrival.class);
            */
        }

        /*
        Intent intent = new Intent(view.getContext(), ClosestStopActivity.class);

        // TODO: Should have the activity launch a new fragment instead of this launching a new activity.
        View parentView = view.getRootView();

        //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)view.getContext(), mRouteNameTextView, "routeName");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)view.getContext(),
                                                                                     Pair.create(parentView.findViewById(R.id.app_bar), "app_bar"));

        /*
        intent.putExtra("ROUTENAME", mRouteNameTextView.getText());
        intent.putExtra("ROUTENUMBER", mRouteNumberTextView.getText());
        intent.putExtra("ROUTECOLOR", routeColor);


        view.getContext().startActivity(intent, options.toBundle());
        */
    }
}
