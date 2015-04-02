package net.spaceboats.busbus.android.Utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by agustafs on 3/11/15.
 */
public class URLBuilder {
    protected static final String SCHEME = "http";
    protected static final String AUTHORITY = "ec2-54-68-11-133.us-west-2.compute.amazonaws.com";
    protected static final String ROUTES = "routes";
    protected static final String STOPS = "stops";
    protected static final String ARRIVALS = "arrivals";
    protected static final String EXPAND_SEPARATOR = ",";
    protected static final String EXPAND = "_expand";
    private Uri.Builder builder;
    private String expandArgs;
    private Context mContext;

    public URLBuilder(Context context, String entity) {
        expandArgs = "";
        mContext = context;
        builder = new Uri.Builder();
        builder.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(entity);
    }

    public void addQueryParam(String name, String value) {
        builder.appendQueryParameter(name, value);
    }

    public void addToExpandArgs(String entityName) {
        expandArgs += entityName + EXPAND_SEPARATOR;
    }

    public String getURL() {
        addQueryParam(EXPAND, expandArgs);
        try {
            URL url = new URL(builder.build().toString());
            return url.toString();
        } catch (MalformedURLException ue) {
            Log.v("URL Error", "Could not generate URL" + ue);
            Toast.makeText(mContext, "Couldn't retrieve data",
                    Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
