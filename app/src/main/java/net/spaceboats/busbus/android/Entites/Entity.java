package net.spaceboats.busbus.android.Entites;

/**
 * Created by zralston on 3/10/15.
 */
public abstract class Entity implements Comparable<Entity>{

    private boolean mIsFavorite;
    protected String mProviderId;

    public Entity(String providerId) {
        this.mIsFavorite = false;
        mProviderId = providerId;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean value) {
        mIsFavorite = value;
    }

    public void setProviderId(String id) {
        mProviderId = id;
    }

    public String getProviderId() {
        return mProviderId;
    }

    public int compareTo(Entity entity) {
        return 0;
    }
}
