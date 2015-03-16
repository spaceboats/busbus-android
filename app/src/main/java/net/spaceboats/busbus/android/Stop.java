package net.spaceboats.busbus.android;

/**
 * Created by zralston on 3/10/15.
 */
public class Stop extends Entity {

    private double mLatitude;
    private double mLongitude;
    private String mName;
    private String mDescription;
    private String mId;

    public Stop(String name, double latitude, double longitude, String description, String stopId) {
        this.mId = stopId;
        this.mName = name;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mDescription = description;
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
}
