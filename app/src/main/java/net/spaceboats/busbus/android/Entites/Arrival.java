package net.spaceboats.busbus.android.Entites;

import android.support.annotation.NonNull;

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

    public Arrival(long timeSeconds, @NonNull String headsign, @NonNull Stop stop, @NonNull Route route) {
        setStop(stop);
        setRoute(route);
        setHeadsign(headsign);
        setTimeInSeconds(timeSeconds);
    }

    public void setStop(@NonNull Stop stop) {
        mStop = stop;
    }

    public void setRoute(@NonNull Route route) {
        mRoute = route;
    }

    public void setHeadsign(@NonNull String headsign) {
        mHeadsign = headsign;
    }

    public void setTimeInSeconds(long timeInSeconds) {
        mTimeSeconds = timeInSeconds;
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
        String units;
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
        return Long.compare(this.getTimeInSeconds(), ((Arrival) (entity)).getTimeInSeconds());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arrival arrival = (Arrival) o;

        //if (mTimeSeconds != arrival.mTimeSeconds) return false;
        //if (!mHeadsign.equals(arrival.mHeadsign)) return false;
        if (!mRoute.equals(arrival.mRoute)) return false;
        if (!mStop.equals(arrival.mStop)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mStop.hashCode();
        result = 31 * result + mRoute.hashCode();
        result = 31 * result + mHeadsign.hashCode();
        result = 31 * result + (int) (mTimeSeconds ^ (mTimeSeconds >>> 32));
        return result;
    }
}
