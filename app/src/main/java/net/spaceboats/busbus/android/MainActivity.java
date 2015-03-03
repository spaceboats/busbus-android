package net.spaceboats.busbus.android;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RouteRecyclerAdapter mRouteAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        // Size will not change as the data changes in an item.
        mRecyclerView.setHasFixedSize(true);

        // Each item should appear one after another
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Route adapter that will handle adding and removing items.
        mRouteAdapter = new RouteRecyclerAdapter();
        mRecyclerView.setAdapter(mRouteAdapter);

        GarbageRouteData.setDefaultRouteData(mRouteAdapter);
    }
}