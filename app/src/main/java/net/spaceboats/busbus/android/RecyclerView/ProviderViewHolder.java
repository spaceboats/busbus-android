package net.spaceboats.busbus.android.RecyclerView;

import android.view.View;
import android.widget.TextView;

import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Provider;
import net.spaceboats.busbus.android.R;

/**
 * Created by zralston on 4/23/15.
 */
public class ProviderViewHolder extends BaseViewHolder<Provider> {

    private TextView mProviderCreditTextView;

    public ProviderViewHolder(View view) {
        super(view);
        mProviderCreditTextView = (TextView) view.findViewById(R.id.providerName);
    }

    public void setData(Entity entity) {
        mEntity = (Provider) entity;
        setProviderName(mEntity.getCredit());
    }

    public void setProviderName(String name) {
        mProviderCreditTextView.setText(name);
    }
}
