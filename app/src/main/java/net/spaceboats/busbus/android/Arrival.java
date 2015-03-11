package net.spaceboats.busbus.android;

/**
 * Created by zralston on 3/10/15.
 */
public class Arrival extends Entity {
    private Stop mStop;
    private Route mRoute;
    private String mHeadsign;
    private long mTimeSeconds;

    public Arrival(long timeSeconds, String headsign, Stop stop, Route route) {
        this.mTimeSeconds = timeSeconds;
        this.mHeadsign = headsign;
        this.mStop = stop;
        this.mRoute = route;
    }

    public Stop getStop() {
        return mStop;
    }

    public Route getRoute() {
        return mRoute;
    }

    public String getHeadsign() {
        return mHeadsign;
    }

    public long getTimeInSeconds() {
        return mTimeSeconds;
    }

    public String getStringOfTimeDifferenceInMinutes (long beforeTimeInSeconds) {
        long difference = mTimeSeconds - beforeTimeInSeconds;
        return Long.toString(difference/60);
    }
}
