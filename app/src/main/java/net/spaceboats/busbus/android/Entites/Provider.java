package net.spaceboats.busbus.android.Entites;

/**
 * Created by zralston on 4/23/15.
 */
public class Provider extends Entity {

    String mCredit;
    String mId;

    public Provider(String credit, String id) {
        setCredit(credit);
        setId(id);
    }

    public void setCredit(String credit) {
        mCredit = credit;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getCredit() {
        return mCredit;
    }

    public String getId() {
        return mId;
    }
}
