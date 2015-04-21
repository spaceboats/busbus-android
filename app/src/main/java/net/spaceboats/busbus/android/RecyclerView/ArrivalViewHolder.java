package net.spaceboats.busbus.android.RecyclerView;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.spaceboats.busbus.android.Entites.Arrival;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.R;

import java.util.Date;

/**
 * Created by zralston on 3/12/15.
 */
class ArrivalViewHolder extends BaseViewHolder<Arrival> {

    private TextView mRouteNumberTextView;
    private ImageView mRouteColorImageView;
    private TextView mStopNameTextView;
    private TextView mNextArrivalTime;
    private ImageView mFavoriteUnfilled;
    private ImageView mFavoriteFilled;

    public ArrivalViewHolder(View view) {
        super(view);
        mRouteNumberTextView = (TextView) view.findViewById(R.id.routeNumber);
        mRouteColorImageView = (ImageView) view.findViewById(R.id.routeColor);
        mStopNameTextView = (TextView) view.findViewById(R.id.stopName);
        mNextArrivalTime = (TextView) view.findViewById(R.id.nextArrivalTime);
        mFavoriteUnfilled = (ImageView) view.findViewById(R.id.favoriteUnfilled);
        mFavoriteFilled = (ImageView) view.findViewById(R.id.favoriteFilled);
    }

    public void setData(Entity entity) {
        mEntity = (Arrival) entity;
        setRouteNumber("Route " + mEntity.getRoute().getNumber());
        setStopName("@ " + mEntity.getStop().getStopName() + " to " + mEntity.getHeadsign());
        setRouteColor(mEntity.getRoute().getColor());
        Date date = new Date();
        // TODO: Fix negative times being shown when user scrolls down then back up.
        setNextArrivalTime(mEntity.getStringOfTimeDiff(date.getTime()/1000));
        setFavorite(entity.isFavorite());
    }

    private void setRouteColor(String color){
        this.mRouteColorImageView.getDrawable().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_OVER);
    }

    public void setRouteNumber(String number) {
        this.mRouteNumberTextView.setText(number);
    }

    public void setStopName(String name) {
        this.mStopNameTextView.setText(name);
    }

    public void setNextArrivalTime(String time) {
        this.mNextArrivalTime.setText(time);
    }

    public void setFavorite(boolean value) {
        if(value) {
            mFavoriteFilled.setVisibility(View.VISIBLE);
            mFavoriteUnfilled.setVisibility(View.GONE);
        }
        else {
            mFavoriteFilled.setVisibility(View.GONE);
            mFavoriteUnfilled.setVisibility(View.VISIBLE);
        }
    }

    public ImageView getFavoritedImageView() {
        return mFavoriteFilled;
    }

    public ImageView getUnFavoritedImageView() {
        return mFavoriteUnfilled;
    }
}
