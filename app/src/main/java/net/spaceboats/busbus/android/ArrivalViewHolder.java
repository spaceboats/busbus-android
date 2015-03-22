package net.spaceboats.busbus.android;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by zralston on 3/12/15.
 */
public class ArrivalViewHolder extends BaseViewHolder {

    private TextView mStopNameTextView;
    private TextView mNextArrivalTime;
    public ImageView mFavorite;
    private Arrival mArrival;

    public ArrivalViewHolder(View view) {
        super(view);
        mStopNameTextView = (TextView) itemView.findViewById(R.id.stopName);
        mNextArrivalTime = (TextView) itemView.findViewById(R.id.nextArrivalTime);
        mFavorite = (ImageView) itemView.findViewById(R.id.favorite);
    }

    public void setData(Entity entity) {
        mArrival = (Arrival) entity;
        setStopName(mArrival.getStop().getStopName());
        Date date = new Date();
        setNextArrivalTime(mArrival.getStringOfTimeDiff(date.getTime()/1000));
    }

    public void setStopName(String name) {
        this.mStopNameTextView.setText(name);
    }

    public void setNextArrivalTime(String time) {
        this.mNextArrivalTime.setText(time);
    }

    public Arrival getArrival() {
        return mArrival;
    }
}
