package net.spaceboats.busbus.android.Entites;

/**
 * Created by zralston on 5/4/15.
 */
public class BlankEntity extends Entity {

    String mMessage;

    public BlankEntity(String msg) {
        super("BLANK");
        mMessage = msg;
    }

    public void setMessage(String msg) {
        mMessage = msg;
    }

    public String getMessage() {
        return mMessage;
    }
}
