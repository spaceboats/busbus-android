package net.spaceboats.busbus.android.RecyclerView;

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

    private TextView mStopNameTextView;
    private TextView mNextArrivalTime;
    private ImageView mFavoriteUnfilled;
    private ImageView mFavoriteFilled;

    public ArrivalViewHolder(View view) {
        super(view);
        mStopNameTextView = (TextView) itemView.findViewById(R.id.stopName);
        mNextArrivalTime = (TextView) itemView.findViewById(R.id.nextArrivalTime);
        mFavoriteUnfilled = (ImageView) itemView.findViewById(R.id.favoriteUnfilled);
        mFavoriteFilled = (ImageView) itemView.findViewById(R.id.favoriteFilled);
    }

    public void setData(Entity entity) {
        mEntity = (Arrival) entity;
        setStopName(mEntity.getStop().getStopName());
        Date date = new Date();
        setNextArrivalTime(mEntity.getStringOfTimeDiff(date.getTime()/1000));
        setFavorite(entity.isFavorite());
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
