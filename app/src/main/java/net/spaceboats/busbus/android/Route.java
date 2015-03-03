package net.spaceboats.busbus.android;

/**
 * Created by zralston on 2/18/15.
 */
public class Route {
    private String number;
    private String color;
    private String name;

    public Route(String number, String name, String color){
        this.number = number;
        this.color = color;
        this.name = name;
    }

    public String getNumber(){
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }
}