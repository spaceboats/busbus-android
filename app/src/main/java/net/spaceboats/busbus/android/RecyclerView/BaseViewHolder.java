package net.spaceboats.busbus.android.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.spaceboats.busbus.android.Entites.Entity;

/**
 * Created by zralston on 3/12/15.
 */
abstract class BaseViewHolder<T extends Entity> extends RecyclerView.ViewHolder {

    protected T mEntity;

    public BaseViewHolder(View view) {
        super(view);
    }

    public T getEntity() {
        return mEntity;
    }

    public abstract void setData(Entity entity);
}
