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
class URLBuilder {
    protected static final String SCHEME = "http";
    protected static final String AUTHORITY = "ec2-54-68-11-133.us-west-2.compute.amazonaws.com";
    protected static final String EXPAND = "_expand";
    protected static final String EXPAND_SEPARATOR = ",";
    protected static final String ENTITY_ATTRIBUTE_SEPARATOR = ".";
    private Uri.Builder builder;
    private String expandArgs;
    private Context mContext;
    private boolean expandAdded;

    public URLBuilder(Context context, String entity) {
        expandArgs = "";
        expandAdded = false;
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

    protected void addEntityAttrParam(String entityName, String attributeName, String value) {
        addQueryParam(entityName + ENTITY_ATTRIBUTE_SEPARATOR + attributeName, value);
    }

    protected void addExpandQueryParam() {
        if(!expandAdded) {
            addQueryParam(EXPAND, expandArgs);
            expandAdded = true;
        }
    }

    protected void addToPath(String pathExtension) {
        builder.appendPath(pathExtension);
    }

    public String getURL() {
        addExpandQueryParam();
        try {
            // TODO: why is there a URL variable here?
            URL url = new URL(builder.build().toString());
            String urlStr = url.toString();
            Log.v(getClass().getName(), urlStr);
            return urlStr;
        } catch (MalformedURLException ue) {
            Log.v(getClass().getName(), "Could not generate URL" + ue);
            // TODO: Remove this toast and probably throw an exception so the activity can make toast
            Toast.makeText(mContext, "Couldn't retrieve data",
                    Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
