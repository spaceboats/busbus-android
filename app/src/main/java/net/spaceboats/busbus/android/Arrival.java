package net.spaceboats.busbus.android;

/**
 * Created by zralston on 3/10/15.
 */
public class Arrival extends Entity {
    private static final int HOUR_IN_SECONDS = 3600;
    private static final int MINUTE_IN_SECONDS = 60;
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

    public String getStringOfTimeDiff(long beforeTimeInSeconds) {
        long difference = (mTimeSeconds - beforeTimeInSeconds);
        String units = "";
        if(difference/HOUR_IN_SECONDS >= 1) {
            difference = difference/HOUR_IN_SECONDS;
            units = " hour";
        }
        else if(difference/MINUTE_IN_SECONDS >= 1) {
            difference = difference/MINUTE_IN_SECONDS;
            units = " mins";
        }
        else
            units = " secs";

        return Long.toString(difference) + units;
    }

    @Override
    public int compareTo(Entity entity) {
        if(!Arrival.class.isInstance(entity))
            return super.compareTo(entity);
        return (int) (this.getTimeInSeconds() -  ((Arrival) (entity)).getTimeInSeconds());
    }
}
