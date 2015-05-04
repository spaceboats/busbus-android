package net.spaceboats.busbus.android.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import net.spaceboats.busbus.android.Entites.Arrival;
import net.spaceboats.busbus.android.Entites.BlankEntity;
import net.spaceboats.busbus.android.Entites.Entity;
import net.spaceboats.busbus.android.Entites.Provider;
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
    private static final int PROVIDER_VIEWTYPE = 8;
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
        if(entities.size() != 0)
            this.entities = entities;
        else {
            List<Entity> tempList = new ArrayList<>();
            tempList.add(new BlankEntity("No Data"));
            this.entities = tempList;
        }
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
        else if(tempEntity instanceof Provider) {
            viewType = PROVIDER_VIEWTYPE;
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
        else if(viewType == PROVIDER_VIEWTYPE) {
            viewHolder = createProviderViewHolder(viewGroup);
        }
        else if(viewType == DEFAULT_VIEWTYPE) {
            viewHolder = createBlankViewHolder(viewGroup);
        }

        return viewHolder;
    }

    private void runEnterAnimation(View view, int position) {
        if (position > lastPositionAnimated) {
            view.setAlpha(0.f);
            view.setTranslationY(150);
            final long MAX_DELAY = 165;
            long startDelay = position * 15 > MAX_DELAY ? MAX_DELAY : position * 15;
            view.animate()
                    .alpha(1.f)
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setStartDelay(startDelay)
                    .setDuration(200)
                    .start();
            lastPositionAnimated = position;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, final int position) {
        runEnterAnimation(viewHolder.itemView, position);
        Entity item = entities.get(position);
        if(!viewHolder.setData(item)) {
            // TODO: See if there is a better way to do this?
            android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    removeItem(position);
                }
            }, 1000);
        }
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

    private ProviderViewHolder createProviderViewHolder(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        final View view = inflater.inflate(R.layout.provider, viewGroup, false);
        final ProviderViewHolder viewHolder = new ProviderViewHolder(view);
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

    private BlankViewHolder createBlankViewHolder(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        final View view = inflater.inflate(R.layout.blank_entity, viewGroup, false);
        final BlankViewHolder viewHolder = new BlankViewHolder(view);
        return viewHolder;
    }
}
