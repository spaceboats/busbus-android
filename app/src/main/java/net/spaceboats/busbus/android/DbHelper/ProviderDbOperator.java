package net.spaceboats.busbus.android.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.spaceboats.busbus.android.Entites.Provider;

class ProviderDbOperator extends BaseDbOperator<Provider> {

    public ProviderDbOperator(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return FavoritesContract.Provider.TABLE_NAME;
    }

    @Override
    protected ContentValues getContentValues(Provider provider) {
        ContentValues values = new ContentValues();
        values.put(FavoritesContract.Provider.COLUMN_ID, provider.getId());
        values.put(FavoritesContract.Provider.COLUMN_CREDIT, provider.getCredit());

        return values;
    }

    @Override
    protected Provider getNewEntity(Cursor cursor) {
        return new Provider(cursor.getString(cursor.getColumnIndex(FavoritesContract.Provider.COLUMN_CREDIT)),
                cursor.getString(cursor.getColumnIndex(FavoritesContract.Provider.COLUMN_ID)));
    }

    @Override
    protected String[] getColumns() {
        return new String[]{FavoritesContract.Provider.COLUMN_ID,
                FavoritesContract.Provider.COLUMN_CREDIT};
    }

    @Override
    protected String getIdSelection() {
        return FavoritesContract.Provider.COLUMN_ID + "= ?";
    }
}
