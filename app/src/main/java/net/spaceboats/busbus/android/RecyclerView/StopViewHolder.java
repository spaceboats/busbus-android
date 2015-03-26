package net.spaceboats.busbus.android.RecyclerView;

import android.view.View;
import android.widget.TextView;

import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Stop;
import net.spaceboats.busbus.android.R;
import net.spaceboats.busbus.android.RecyclerView.BaseViewHolder;

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