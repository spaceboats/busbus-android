package net.spaceboats.busbus.android;

import android.view.View;
import android.widget.TextView;

/**
 * Created by zralston on 3/12/15.
 */
public class StopViewHolder extends BaseViewHolder {

    private TextView mStopNameTextView;
    private Stop mStop;

    public StopViewHolder(View view) {
        super(view);
        mStopNameTextView = (TextView) itemView.findViewById(R.id.stopName);
    }

    public void setData(Entity entity) {
        mStop = (Stop) entity;
        setStopName(mStop.getStopName());
    }

    private void setStopName(String name) {
        this.mStopNameTextView.setText(name);
    }

    public Stop getStop() {
        return mStop;
    }
}
