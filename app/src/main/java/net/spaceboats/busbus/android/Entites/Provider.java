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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Provider provider = (Provider) o;

        if (!mCredit.equals(provider.mCredit)) return false;
        if (!mId.equals(provider.mId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mCredit.hashCode();
        result = 31 * result + mId.hashCode();
        return result;
    }
}
