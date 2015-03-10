package net.spaceboats.busbus.android;


import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;


public class RecyclerViewFragment extends Fragment {

    public final static String KEY_X_CLICKED_POSITION = "X_CLICKED_POSITION";
    public final static String KEY_Y_CLICKED_POSITION = "Y_CLICKED_POSITION";

    private RecyclerView mRecyclerView;
    private RouteRecyclerAdapter mRouteAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private View rootView;


    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    public static RecyclerViewFragment newinstance(int cx, int cy) {
        RecyclerViewFragment myFragment = new RecyclerViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_X_CLICKED_POSITION, cx);
        bundle.putInt(KEY_Y_CLICKED_POSITION, cy);
        myFragment.setArguments(bundle);

        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        // Size will not change as the data changes in an item.
        mRecyclerView.setHasFixedSize(true);

        // Each item should appear one after another
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Route adapter that will handle adding and removing items.
        mRouteAdapter = new RouteRecyclerAdapter();
        mRecyclerView.setAdapter(mRouteAdapter);


        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);

                int finalRadius = Math.max(v.getWidth(), v.getHeight());

                Animator anim =
                        ViewAnimationUtils.createCircularReveal(v,
                                getArguments().getInt(KEY_X_CLICKED_POSITION, 0),
                                getArguments().getInt(KEY_Y_CLICKED_POSITION, 0),
                                0, finalRadius);

                anim.setDuration(1300);

                v.setVisibility(View.VISIBLE);
                anim.start();
            }
        });

        return rootView;
    }

    public RouteRecyclerAdapter getRouteAdapter() {
        return mRouteAdapter;
    }
}
