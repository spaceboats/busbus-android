package net.spaceboats.busbus.android;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.drawable.ShapeDrawable;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zralston on 2/18/15.
 */
public class RouteRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mRouteNumberTextView;
    private TextView mRouteNameTextView;
    //private CardView mCardView;
    private ImageView mColorImageView;
    String color = "#000000";

    public RouteRecyclerViewHolder(View itemView) {
        super(itemView);
        mRouteNumberTextView = (TextView) itemView.findViewById(R.id.routeNumber);
        //mCardView = (CardView) itemView.findViewById(R.id.mbg);
        mColorImageView = (ImageView) itemView.findViewById(R.id.mbg);
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
        this.mColorImageView.getDrawable().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_OVER);
        this.color = color;
    }

    public void setRouteName(String name){
        this.mRouteNameTextView.setText(name);
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
        intent.putExtra("ROUTECOLOR", color);

        view.getContext().startActivity(intent, options.toBundle());
    }
}
