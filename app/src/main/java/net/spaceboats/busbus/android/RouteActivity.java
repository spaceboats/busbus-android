package net.spaceboats.busbus.android;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.widget.LinearLayout;
import android.widget.TextView;


public class RouteActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);
        //postponeEnterTransition();

        TextView tv1 = (TextView) findViewById(R.id.textView);
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        LinearLayout l1 = (LinearLayout) findViewById(R.id.stuff);

        tv1.setText(getIntent().getExtras().getString("ROUTENUMBER"));
        tv2.setText(getIntent().getExtras().getString("ROUTENAME"));
        l1.setBackgroundColor(Color.parseColor(getIntent().getExtras().getString("ROUTECOLOR")));
    }
}
