package net.spaceboats.busbus.android;

import android.view.View;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by zralston on 3/12/15.
 */
public class ArrivalViewHolder extends BaseViewHolder {

    private TextView mStopNameTextView;
    private TextView mNextArrivalTime;
    private Arrival mArrival;

    public ArrivalViewHolder(View view) {
        super(view);
        mStopNameTextView = (TextView) itemView.findViewById(R.id.stopName);
        mNextArrivalTime = (TextView) itemView.findViewById(R.id.nextArrivalTime);
    }

    public void setData(Entity entity) {
        mArrival = (Arrival) entity;
        setStopName(mArrival.getStop().getStopName());
        Date date = new Date();
        //setNextArrivalTime(mArrival.getStringOfTimeDifferenceInMinutes(date.getTime()/1000) + " mins");
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
