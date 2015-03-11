package net.spaceboats.busbus.android;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zralston on 2/18/15.
 */
public class MyRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mRouteNumberTextView;
    private TextView mRouteNameTextView;
    //private CardView mCardView;
    private ImageView mRouteColorImageView;
    String routeColor = "#000000";

    public MyRecyclerViewHolder(View itemView) {
        super(itemView);
        mRouteNumberTextView = (TextView) itemView.findViewById(R.id.routeNumber);
        //mCardView = (CardView) itemView.findViewById(R.id.mbg);
        mRouteColorImageView = (ImageView) itemView.findViewById(R.id.mbg);
        mRouteNameTextView = (TextView) itemView.findViewById(R.id.routeName);

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

    public void setData(Entity entity) {
        if(Route.class.isInstance(entity)) {
            Route route = (Route) entity;

            setRouteNumber(route.getNumber());
            setBackgroundColor(route.getColor());
            setRouteName(route.getName());
        }
        else if(Stop.class.isInstance(entity)) {
            //TODO: FIX THIS
        }
    }

    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), RouteActivity.class);

        // TODO: Should have the activity launch a new fragment instead of this launching a new activity.
        View parentView = view.getRootView();

        //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)view.getContext(), mRouteNameTextView, "routeName");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)view.getContext(), Pair.create((View)mRouteNameTextView, "routeName"),
                                                                                     Pair.create((View)mRouteNumberTextView, "routeNumber"),
                                                                                     //Pair.create((View)mCardView, "mgb"),
                                                                                     Pair.create(parentView.findViewById(R.id.app_bar), "app_bar"));

        intent.putExtra("ROUTENAME", mRouteNameTextView.getText());
        intent.putExtra("ROUTENUMBER", mRouteNumberTextView.getText());
        intent.putExtra("ROUTECOLOR", routeColor);

        view.getContext().startActivity(intent, options.toBundle());
    }
}
