package net.spaceboats.busbus.android.RecyclerView;

import android.view.View;
import android.widget.TextView;

import net.spaceboats.busbus.android.Entites.BlankEntity;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.R;

/**
 * Created by zralston on 5/4/15.
 */
class BlankViewHolder extends BaseViewHolder<BlankEntity> {

    private TextView mMessageTextView;

    public BlankViewHolder(View view) {
        super(view);
        mMessageTextView = (TextView) itemView.findViewById(R.id.message);
    }

    public boolean setData(Entity entity) {
        mEntity = (BlankEntity) entity;
        setStopName(mEntity.getMessage());

        return true;
    }

    private void setStopName(String name) {
        this.mMessageTextView.setText(name);
    }
}
