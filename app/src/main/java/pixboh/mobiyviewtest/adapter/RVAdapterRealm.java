package pixboh.mobiyviewtest.adapter;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pixboh.mobiyviewtest.R;
import pixboh.mobiyviewtest.realm.DataItemRealm;
import pixboh.mobiyviewtest.utils.DistanceUtil;

/**
 * Created by pix on 1/12/18.
 */

public class RVAdapterRealm extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<DataItemRealm> list;
    Context context;
    boolean bottomReached = false;
    public Location cuurentPosition;
    OnBottomReachedInterface mOnBottomReachedInterface;


    public RVAdapterRealm(ArrayList<DataItemRealm> list, Context context, Location currentLocation) {
        this.list = list;
        this.context = context;
        cuurentPosition = currentLocation;

    }

    public void addCurrentLocation(Location location) {
        cuurentPosition = location;
    }

    public void addData(ArrayList<DataItemRealm> newData) {
        list.addAll(newData);
    }

    public void setOnBottomReachedListner(OnBottomReachedInterface mOnBottomReachedInterface) {
        if (this.mOnBottomReachedInterface != null) {
            return;
        } else {
            this.mOnBottomReachedInterface = mOnBottomReachedInterface;
        }

    }

    public OnBottomReachedInterface getmOnBottomReachedInterface() {
        return mOnBottomReachedInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
                RestaurantViewHolder restaurantViewHolder = new RestaurantViewHolder(itemView);
                return restaurantViewHolder;
            }
            case 1: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.attraction_item, parent, false);
                AttractionViewHolder attractionViewHolder = new AttractionViewHolder(itemView);
                return attractionViewHolder;
            }
            default: {
                return null;
            }

        }

    }


    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType() != null) {
            return list.get(position).getType().contains("attraction") ? 1 : 0;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.e("Position", position + "");
        Log.e("End", list.size() + "");
        if (bottomReached == false)
            if (position == list.size() - 1) {
//            bottomReached = true;
//                Log.e("Reach", "Adpater");
                mOnBottomReachedInterface.onBottomReached(position);
            } else {
                bottomReached = false;
            }
        DataItemRealm dataItemRealm = list.get(position);
        //Place holder for miles
        String distance = "???";
        if (cuurentPosition != null && dataItemRealm.getLatlong() != null) {
            Log.e("Currrent lat adapter", cuurentPosition.getLatitude() + "");
            distance = "" + (int) DistanceUtil.distance(cuurentPosition.getLatitude(),
                    cuurentPosition.getLongitude(),
                    Double.valueOf(dataItemRealm.getLatlong().split(",")[0]),
                    Double.valueOf(dataItemRealm.getLatlong().split(",")[1]),
                    'M');
        } else {

        }
        if (holder.getItemViewType() == 0) {
            RestaurantViewHolder restaurantViewHolder = (RestaurantViewHolder) holder;
            restaurantViewHolder.mTitle.setText(dataItemRealm.getTitle());
            restaurantViewHolder.mLocation.setText(dataItemRealm.getLocationArea());
            restaurantViewHolder.mDistance.setText(distance + " miles");

            if (dataItemRealm.getImage() != null) {
                Picasso.with(context).load(dataItemRealm.getImage())
                        .placeholder(R.drawable.placeholder_restaurant)
                        .error(R.drawable.placeholder_restaurant)
                        .into(restaurantViewHolder.mImage);
            } else {
                Picasso.with(context).load("null")
                        .placeholder(R.drawable.placeholder_restaurant)
                        .error(R.drawable.placeholder_restaurant)
                        .into(restaurantViewHolder.mImage);
            }
            return;

        }
        if (holder.getItemViewType() == 1) {

            AttractionViewHolder attractionViewHolder = (AttractionViewHolder) holder;
            attractionViewHolder.mTitle.setText(dataItemRealm.getTitle());
            if(dataItemRealm.getPrice() != null)
            attractionViewHolder.mPrice.setText(dataItemRealm.getPrice());
            attractionViewHolder.mCategorie.setText(dataItemRealm.getAttractionCategory());
            attractionViewHolder.mDistance.setText(distance + " miles");
            attractionViewHolder.mLocation.setText(dataItemRealm.getLocationArea());
            if (dataItemRealm.getImage() != null)
                Picasso.with(context).load(dataItemRealm.getImage())
                        .placeholder(R.drawable.placeholder_attraction)
                        .error(R.drawable.placeholder_attraction)
                        .into(attractionViewHolder.mImage);
            else {
                Picasso.with(context).load("null")
                        .placeholder(R.drawable.placeholder_attraction)
                        .error(R.drawable.placeholder_attraction)
                        .into(attractionViewHolder.mImage);
            }

            return;

        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restau_cv)
        CardView mCardView;
        @BindView(R.id.restau_title)
        TextView mTitle;
        @BindView(R.id.restau_location)
        TextView mLocation;
        @BindView(R.id.restau_distance)
        TextView mDistance;
        @BindView(R.id.restau_image)
        ImageView mImage;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class AttractionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.attraction_cv)
        CardView mCardView;
        @BindView(R.id.attraction_title)
        TextView mTitle;
        @BindView(R.id.attraction_location)
        TextView mLocation;
        @BindView(R.id.attraction_distance)
        TextView mDistance;
        @BindView(R.id.attraction_image)
        ImageView mImage;
        @BindView(R.id.attraction_price)
        TextView mPrice;
        @BindView(R.id.attraction_categorie)
        TextView mCategorie;

        public AttractionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnBottomReachedInterface {
        public void onBottomReached(int position);
    }


}
