package net.spaceboats.busbus.android;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


public class RouteActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        postponeEnterTransition();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);

        TextView tv1 = (TextView) findViewById(R.id.textView);
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        LinearLayout l1 = (LinearLayout) findViewById(R.id.stuff);
        RecyclerView rc = (RecyclerView) findViewById(R.id.recycler);
        // Size will not change as the data changes in an item.
        rc.setHasFixedSize(true);

        // Each item should appear one after another
        rc.setLayoutManager(new LinearLayoutManager(this));

        // Route adapter that will handle adding and removing items.
        RouteRecyclerAdapter ra = new RouteRecyclerAdapter();
        rc.setAdapter(ra);

        GarbageRouteData.setDefaultStopData(ra);

        tv1.setText(getIntent().getExtras().getString("ROUTENUMBER"));
        tv2.setText(getIntent().getExtras().getString("ROUTENAME"));
        l1.setBackgroundColor(Color.parseColor(getIntent().getExtras().getString("ROUTECOLOR")));

        startPostponedEnterTransition();
    }
}
