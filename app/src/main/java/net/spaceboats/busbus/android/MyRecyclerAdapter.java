package net.spaceboats.busbus.android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zralston on 2/18/15.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {

    private List<Entity> entities = new ArrayList<>();

    public MyRecyclerAdapter() {

    }

    public void updateItems(List<Entity> entities) {
        this.entities = entities;
        notifyDataSetChanged();
    }

    public void addItem(int position, Entity entity){
        if(entity != null) {
            entities.add(position, entity);
            notifyItemInserted(position);
        }
    }

    public void removeItem(int position){
        entities.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View routeView = inflater.inflate(R.layout.cardview_entity, viewGroup, false);
        return new MyRecyclerViewHolder(routeView);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder viewHolder, int position) {
        Entity item = entities.get(position);
        viewHolder.setData(item);
    }
}
