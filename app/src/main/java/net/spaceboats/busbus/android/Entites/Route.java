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
    private String compareableShortName;

    public Route(@NonNull String shortName, @NonNull String name, @NonNull String color, @NonNull String routeId, @NonNull String providerId){
        super(providerId);
        setShortName(shortName);
        setColor(color);
        setName(name);
        setId(routeId);
        compareableShortName = makeLengthTenOrMore(shortName);
    }

    private String makeLengthTenOrMore(String original) {
        // A hack to make the string 10 come after 1
        // Note: Cannot just convert to integer, because some of the shortNames are character strings
        StringBuilder sb = new StringBuilder("0000000000");
        StringBuilder orig = new StringBuilder(original).reverse();
        if(sb.length() > orig.length()) {
            orig.append(sb, orig.length(), sb.length());
            return orig.reverse().toString();
        }
        else {
            return original;
        }
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
        if (!mProviderId.equals(route.mProviderId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shortName.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + mId.hashCode();
        result = 31 * result + mProviderId.hashCode();
        return result;
    }

    @Override
    public int compareTo(Entity entity) {
        if(!Route.class.isInstance(entity))
            return super.compareTo(entity);

        return this.compareableShortName.compareTo(((Route) entity).compareableShortName);
    }
}
