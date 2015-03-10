package net.spaceboats.busbus.android;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private int clicked_x_coord = 0;
    private int clicked_y_coord = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);

        final CardView cardView = (CardView) findViewById(R.id.closestStops);

        cardView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    clicked_x_coord = (int) event.getX();
                    clicked_y_coord = (int) event.getY();
                }

                // return false so click events are not blocked
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        View parentView = view.getRootView();

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(parentView.findViewById(R.id.app_bar), "app_bar"));

        intent.putExtra(getString(R.string.EXTRA_X_CLICKED_POSITION), clicked_x_coord);
        intent.putExtra(getString(R.string.EXTRA_Y_CLICKED_POSITION), clicked_y_coord);
        startActivity(intent, options.toBundle());
    }

    public void launchClosestStop(View view) {
        Intent intent = new Intent(this, ClosestStopActivity.class);

        View parentView = view.getRootView();

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(parentView.findViewById(R.id.app_bar), "app_bar"));

        intent.putExtra(getString(R.string.EXTRA_X_CLICKED_POSITION), clicked_x_coord);
        intent.putExtra(getString(R.string.EXTRA_Y_CLICKED_POSITION), clicked_y_coord);
        startActivity(intent, options.toBundle());
    }
}
