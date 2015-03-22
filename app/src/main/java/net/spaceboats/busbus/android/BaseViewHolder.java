package net.spaceboats.busbus.android;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zralston on 3/12/15.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View view) {
        super(view);
    }

    public abstract void setData(Entity entity);
}
