package net.spaceboats.busbus.android.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import net.spaceboats.busbus.android.Entites.Arrival;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Route;
import net.spaceboats.busbus.android.Entites.Stop;
import net.spaceboats.busbus.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zralston on 2/18/15.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    // TODO: Come up with a way to display route/stop sections for arrivals,
    // so we don't have to keep displaying the same route name on each item.
    private static final int DEFAULT_VIEWTYPE = 0;
    private static final int ROUTE_VIEWTYPE = 1;
    private static final int STOP_VIEWTYPE = 2;
    private static final int ARRIVAL_VIEWTYPE = 4;
    private MyClickListener mClickListener;

    private List<Entity> entities = new ArrayList<>();

    private int lastPositionAnimated = -1;

    // Whatever creates this adapter must implement this interface, so we know
    // what function to call when an item is clicked.
    public interface MyClickListener {
        public void entityClicked(Entity entity);
        public void entityFavorited(Entity entity);
        public void entityUnFavorited(Entity entity, int position);
    }

    public MyRecyclerAdapter(MyClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void updateItems(List<Entity> entities) {
        this.entities = entities;
        notifyDataSetChanged();
    }

    public void addItemToEnd(Entity entity) {
        addItem(getItemCount(), entity);

        // Apparently notifyItemInserted(position) in addItem() doesn't allow for the animation in
        // the bindViewHolder() to run correctly, so this call has to happen.
        notifyDataSetChanged();
    }

    public void addItem(int position, Entity entity){
        if(entity != null && position <= getItemCount() && position >= 0) {
            entities.add(position, entity);
            notifyItemInserted(position);
        }
    }

    public void removeItem(int position){
        if(position < getItemCount()) {
            entities.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Entity tempEntity = entities.get(position);
        int viewType = DEFAULT_VIEWTYPE;

        if(tempEntity instanceof Route) {
            viewType = ROUTE_VIEWTYPE;
        }
        else if(tempEntity instanceof Stop) {
            viewType = STOP_VIEWTYPE;
        }
        else if(tempEntity instanceof Arrival) {
            viewType = ARRIVAL_VIEWTYPE;
        }

        return viewType;
    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        BaseViewHolder viewHolder = null;

        if(viewType == ROUTE_VIEWTYPE) {
            viewHolder = createRouteViewHolder(viewGroup);
        }
        else if(viewType == STOP_VIEWTYPE) {
            viewHolder = createStopViewHolder(viewGroup);
        }
        else if(viewType == ARRIVAL_VIEWTYPE) {
            viewHolder = createArrivalViewHolder(viewGroup);
        }

        return viewHolder;
    }

    private void runEnterAnimation(View view, int position) {
        if (position > lastPositionAnimated) {
            lastPositionAnimated = position;
            view.setAlpha(0.f);
            view.setTranslationY(150);
            view.animate()
                    .alpha(1.f)
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setStartDelay(position * 15)
                    .setDuration(200)
                    .start();
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        runEnterAnimation(viewHolder.itemView, position);
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
                mClickListener.entityClicked(viewHolder.getEntity());
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
                mClickListener.entityClicked(viewHolder.getEntity());
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
                mClickListener.entityClicked(viewHolder.getEntity());
            }
        });
        viewHolder.getUnFavoritedImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entity entity = viewHolder.getEntity();
                if(!entity.isFavorite()) {
                    mClickListener.entityFavorited(viewHolder.getEntity());
                    viewHolder.setFavorite(true);
                }
            }
        });
        viewHolder.getFavoritedImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entity entity = viewHolder.getEntity();
                if(entity.isFavorite()) {
                    mClickListener.entityUnFavorited(viewHolder.getEntity(), viewHolder.getAdapterPosition());
                    viewHolder.setFavorite(false);
                }
            }
        });
        return viewHolder;
    }
}
