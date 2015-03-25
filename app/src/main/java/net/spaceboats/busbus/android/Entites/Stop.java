package net.spaceboats.busbus.android.Entites;

import android.support.annotation.NonNull;

/**
 * Created by zralston on 3/10/15.
 */
public class Stop extends Entity {

    private double mLatitude;
    private double mLongitude;
    private String mName;
    private String mDescription;
    private String mId;

    public Stop(@NonNull String name, double latitude, double longitude, @NonNull String description, @NonNull String stopId) {
        setId(stopId);
        setName(name);
        setDescription(description);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public void setName(@NonNull String name) {
        mName = name;
    }

    public void setDescription(@NonNull String description) {
        mDescription = description;
    }

    public void setId(@NonNull String id) {
        mId = id;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public String getStopName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getId() {
        return mId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stop stop = (Stop) o;

        if (Double.compare(stop.mLatitude, mLatitude) != 0) return false;
        if (Double.compare(stop.mLongitude, mLongitude) != 0) return false;
        if (!mDescription.equals(stop.mDescription)) return false;
        if (!mId.equals(stop.mId)) return false;
        if (!mName.equals(stop.mName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(mLatitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mLongitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + mName.hashCode();
        result = 31 * result + mDescription.hashCode();
        result = 31 * result + mId.hashCode();
        return result;
    }
}
