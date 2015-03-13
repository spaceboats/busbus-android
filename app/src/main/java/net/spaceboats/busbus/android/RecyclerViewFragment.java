package net.spaceboats.busbus.android;


import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;


public class RecyclerViewFragment extends Fragment {

    public final static String KEY_X_CLICKED_POSITION = "X_CLICKED_POSITION";
    public final static String KEY_Y_CLICKED_POSITION = "Y_CLICKED_POSITION";

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PassBackData mPassBackData;

    private View rootView;

    public interface PassBackData {
        public void itemClicked(Entity entity);
    }


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
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // makes sure that the activity implements this interface
        mPassBackData = (PassBackData) activity;
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
        mAdapter = new MyRecyclerAdapter(new MyRecyclerAdapter.MyClickListener() {
            public void entityClicked(Entity entity) {
                mPassBackData.itemClicked(entity);
            }
        });
        mRecyclerView.setAdapter(mAdapter);


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

    public void updateData(List<Entity> entities) {
        mAdapter.updateItems(entities);
    }
}
