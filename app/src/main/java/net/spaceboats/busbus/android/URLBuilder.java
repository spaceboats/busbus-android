package net.spaceboats.busbus.android;

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
    public static final String SCHEME = "http";
    public static final String AUTHORITY = "ec2-54-68-11-133.us-west-2.compute.amazonaws.com";
    public static final String ROUTES = "routes";
    public static final String STOPS = "stops";
    public static final String ARRIVALS = "arrivals";
    private Uri.Builder builder;
    Context mcontext;

    public URLBuilder(Context context, String entity) {
        mcontext = context;
        builder = new Uri.Builder();
        builder.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(entity);
    }

    public void addQueryParam(String name, String value) {
        builder.appendQueryParameter(name, value);
    }

    public String getURL() {
        try {
            URL url = new URL(builder.build().toString());
            return url.toString();
        } catch (MalformedURLException ue) {
            Log.v("URL Error", "Could not generate URL" + ue);
            Toast.makeText(mcontext, "Couldn't retrieve data",
                    Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
