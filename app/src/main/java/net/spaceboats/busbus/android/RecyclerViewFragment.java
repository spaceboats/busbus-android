package net.spaceboats.busbus.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.spaceboats.busbus.android.DbHelper.EntityDbDelegator;
import net.spaceboats.busbus.android.Entites.Arrival;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Provider;
import net.spaceboats.busbus.android.RecyclerView.MyRecyclerAdapter;

import java.util.Collections;
import java.util.List;


public class RecyclerViewFragment extends Fragment {

    public final static String KEY_X_CLICKED_POSITION = "X_CLICKED_POSITION";
    public final static String KEY_Y_CLICKED_POSITION = "Y_CLICKED_POSITION";

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyRecyclerAdapter.MyClickListener mPassBackData;

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // makes sure that the activity implements this interface
        mPassBackData = (MyRecyclerAdapter.MyClickListener) activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EntityDbDelegator.initDbDelegator(getActivity().getApplicationContext());

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        // Size will not change as the data changes in an item.
        mRecyclerView.setHasFixedSize(true);

        // Each item should appear one after another
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Route adapter that will handle adding and removing items.
        mAdapter = new MyRecyclerAdapter(mPassBackData);
        mRecyclerView.setAdapter(mAdapter);

/*
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
        */

        return rootView;
    }

    /*
    Summary: Overwrites the data currently in the recyclerView
    Note: The entities are sorted before being inserted based on the entities' compareTo function
     */
    public void updateData(List<Entity> entities) {
        sortEntities(entities);
        markFavorites(entities);
        mAdapter.updateItems(entities);
    }

    /*
    Summary: Adds the entity to the end of the recyclerView
     */
    public void addEntity(Entity entity) {
        markFavorites(entity);
        mAdapter.addItemToEnd(entity);
    }

    /*
    Summary: Removes the entity at the position in the recyclerView
     */
    public void removeEntity(int position) {
        mAdapter.removeItem(position);
    }

    /*
    Summary: Sorts the List of entities by using the entities compareTo function
     */
    public void sortEntities(List<Entity> entities) {
        if(entities.size() != 0)
            Collections.sort(entities);
    }

    /*
    Summary: Will set all entities' favorite attribute to true if it is a favorite.
     */
    public void markFavorites(List<Entity> entityList) {
        for (Entity entity : entityList) {
            markFavorites(entity);
        }
    }

    // TODO: Find somewhere else to put this function and the one above.
    /*
    Summary: Sets the entity favorite attribute to true if it is a favorite
     */
    public void markFavorites(Entity entity) {
        if(entity instanceof Arrival)
            markFavorites(entity, EntityDbDelegator.queryArrivals());
        else if(entity instanceof Provider)
            markFavorites(entity, EntityDbDelegator.queryProviders());
    }

    /*
    Summary: Sets the entity favorite attribute to true if it is in the favorites list
     */
    public void markFavorites(Entity entity, List<Entity> favorites) {
        for(Entity favorite : favorites) {
            if(favorite.equals(entity))
                entity.setFavorite(true);
        }
    }
}
