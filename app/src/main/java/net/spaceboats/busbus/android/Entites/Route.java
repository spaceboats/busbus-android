package net.spaceboats.busbus.android.Entites;

import android.support.annotation.NonNull;

/**
 * Created by zralston on 2/18/15.
 */
public class Route extends Entity {
    private String shortName;
    private String color;
    private String name;
    private String mId;

    public Route(@NonNull String shortName, @NonNull String name, @NonNull String color, @NonNull String routeId){
        setShortName(shortName);
        setColor(color);
        setName(name);
        setId(routeId);
    }

    public String getShortName(){
        return shortName;
    }

    public void setShortName(@NonNull String number){
        this.shortName = number;
    }

    public String getName(){
        return name;
    }

    public void setName(@NonNull String name){
        this.name = name;
    }

    public String getColor(){
        return color;
    }

    public void setColor(@NonNull String color){
        if(color.charAt(0) != '#')
            color = '#' + color;
        this.color = color;
    }

    public String getId() {
        return mId;
    }

    public void setId(@NonNull String id) {
        this.mId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (!color.equals(route.color)) return false;
        if (!mId.equals(route.mId)) return false;
        if (!name.equals(route.name)) return false;
        if (!shortName.equals(route.shortName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shortName.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + mId.hashCode();
        return result;
    }

    /*
    @Override
    public int compareTo(Entity entity) {
        if(!Route.class.isInstance(entity))
            return super.compareTo(entity);
        return Long.compare(this.getTimeInSeconds(), ((Arrival) (entity)).getTimeInSeconds());
    }
    */
}
