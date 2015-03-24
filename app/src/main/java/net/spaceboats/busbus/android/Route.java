package net.spaceboats.busbus.android;

import android.support.annotation.NonNull;

/**
 * Created by zralston on 2/18/15.
 */
public class Route extends Entity {
    private String number;
    private String color;
    private String name;
    private String mId;

    public Route(@NonNull String number, @NonNull String name, @NonNull String color, @NonNull String routeId){
        setNumber(number);
        setColor(color);
        setName(name);
        setId(routeId);
    }

    public String getNumber(){
        return number;
    }

    public void setNumber(@NonNull String number){
        this.number = number;
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
        if (!number.equals(route.number)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = number.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + mId.hashCode();
        return result;
    }
}
