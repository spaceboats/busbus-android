package net.spaceboats.busbus.android.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.spaceboats.busbus.android.Entites.Entity;

/**
 * Created by zralston on 3/12/15.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View view) {
        super(view);
    }

    public abstract void setData(Entity entity);
}
