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
public class MyRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int DEFAULT_VIEWTYPE = 0;
    private static final int ROUTE_VIEWTYPE = 1;
    private static final int STOP_VIEWTYPE = 2;
    private static final int ARRIVAL_VIEWTYPE = 4;
    private MyClickListener mClickListener;

    private List<Entity> entities = new ArrayList<>();

    // Whatever creates this adapter must implement this interface, so we know
    // what function to call when an item is clicked.
    public interface MyClickListener {
        public void entityClicked(Entity entity);
        public void favoriteClicked(Entity entity);
    }

    public MyRecyclerAdapter(MyClickListener clickListener) {
        mClickListener = clickListener;
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
    public int getItemViewType(int position) {
        if(Route.class.isInstance(entities.get(position))) {
            return ROUTE_VIEWTYPE;
        }
        else if(Stop.class.isInstance(entities.get(position))) {
            return STOP_VIEWTYPE;
        }
        else if(Arrival.class.isInstance(entities.get(position))) {
            return ARRIVAL_VIEWTYPE;
        }

        return DEFAULT_VIEWTYPE;
    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType == ROUTE_VIEWTYPE) {
            return createRouteViewHolder(viewGroup);
        }
        else if(viewType == STOP_VIEWTYPE) {
            return createStopViewHolder(viewGroup);
        }
        else if(viewType == ARRIVAL_VIEWTYPE) {
            return createArrivalViewHolder(viewGroup);
        }

        //TODO: Maybe don't return null?
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        Entity item = entities.get(position);
        viewHolder.setData(item);
    }

    private RouteViewHolder createRouteViewHolder(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        final View routeView = inflater.inflate(R.layout.route, viewGroup, false);
        final RouteViewHolder viewHolder = new RouteViewHolder(routeView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.entityClicked(viewHolder.getRoute());
            }
        });
        return viewHolder;
    }

    private StopViewHolder createStopViewHolder(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        final View routeView = inflater.inflate(R.layout.stop, viewGroup, false);
        final StopViewHolder viewHolder = new StopViewHolder(routeView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.entityClicked(viewHolder.getStop());
            }
        });
        return viewHolder;
    }

    private ArrivalViewHolder createArrivalViewHolder(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        final View routeView = inflater.inflate(R.layout.arrival, viewGroup, false);
        final ArrivalViewHolder viewHolder = new ArrivalViewHolder(routeView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.entityClicked(viewHolder.getArrival());
            }
        });
        viewHolder.getFavoriteImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entity entity = viewHolder.getArrival();
                if(!entity.isFavorite()) {
                    mClickListener.favoriteClicked(viewHolder.getArrival());
                    viewHolder.setFavorite(true);
                }
            }
        });
        return viewHolder;
    }
}
