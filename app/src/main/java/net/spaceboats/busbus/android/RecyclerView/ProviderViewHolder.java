package net.spaceboats.busbus.android.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Provider;
import net.spaceboats.busbus.android.R;

/**
 * Created by zralston on 4/23/15.
 */
class ProviderViewHolder extends BaseViewHolder<Provider> {

    private TextView mProviderCreditTextView;
    private ImageView mFavoriteUnfilled;
    private ImageView mFavoriteFilled;

    public ProviderViewHolder(View view) {
        super(view);
        mProviderCreditTextView = (TextView) view.findViewById(R.id.providerName);
        mFavoriteUnfilled = (ImageView) view.findViewById(R.id.favoriteUnfilled);
        mFavoriteFilled = (ImageView) view.findViewById(R.id.favoriteFilled);
    }

    public boolean setData(Entity entity) {
        mEntity = (Provider) entity;
        setProviderName(mEntity.getCredit());
        setFavorite(mEntity.isFavorite());
        
        return true;
    }

    public void setProviderName(String name) {
        mProviderCreditTextView.setText(name);
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
