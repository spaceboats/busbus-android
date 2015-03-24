package net.spaceboats.busbus.android;

/**
 * Created by zralston on 3/10/15.
 */
public abstract class Entity implements Comparable<Entity>{

    private boolean mIsFavorite;

    public Entity() {
        this.mIsFavorite = false;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean value) {
        mIsFavorite = value;
    }

    public int compareTo(Entity entity) {
        return 0;
    }
}
