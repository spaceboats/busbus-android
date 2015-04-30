package net.spaceboats.busbus.android.DbHelper;

import android.provider.BaseColumns;

/**
 * Created by zralston on 3/15/15.
 */
final class FavoritesContract {

    public FavoritesContract() { }

    public static abstract class Stop implements BaseColumns {
        public static String TABLE_NAME = "STOP";
        public static String COLUMN_ID = "ID";
        public static String COLUMN_NAME = "NAME";
        public static String COLUMN_LATITUDE = "LATITUDE";
        public static String COLUMN_LONGITUDE = "LONGITUDE";
        public static String COLUMN_DESCRIPTION = "DESCRIPTION";
    }

    public static abstract class Route implements BaseColumns {
        public static String TABLE_NAME = "ROUTE";
        public static String COLUMN_ID = "ID";
        public static String COLUMN_NAME = "NAME";
        public static String COLUMN_SHORT_NAME= "SHORT_NAME";
        public static String COLUMN_COLOR = "COLOR";
    }

    public static abstract class Arrival implements BaseColumns {
        public static String TABLE_NAME = "ARRIVAL";
        public static String COLUMN_ROUTE_ID = "ROUTE_ID";
        public static String COLUMN_STOP_ID = "STOP_ID";
    }

    public static abstract class Provider implements BaseColumns {
        public static String TABLE_NAME = "PROVIDER";
        public static String COLUMN_ID = "ID";
        public static String COLUMN_CREDIT = "CREDIT";
    }
}
