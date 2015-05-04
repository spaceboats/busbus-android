package net.spaceboats.busbus.android.Entites;

/**
 * Created by zralston on 4/23/15.
 */
public class Provider extends Entity {

    String mCredit;

    public Provider(String credit, String id) {
        super(id);
        setCredit(credit);
    }

    public void setCredit(String credit) {
        mCredit = credit;
    }

    public String getCredit() {
        return mCredit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Provider provider = (Provider) o;

        if (!mCredit.equals(provider.mCredit)) return false;
        if (!mProviderId.equals(provider.mProviderId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mCredit.hashCode();
        result = 31 * result + mProviderId.hashCode();
        return result;
    }
}
