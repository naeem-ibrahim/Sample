package com.algorepublic.brandmaker.ui.stores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import com.algorepublic.brandmaker.BMApp;
import com.algorepublic.brandmaker.BaseActivity;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.model.StoresModel;
import com.algorepublic.brandmaker.ui.dashboard.MainActivity;
import com.algorepublic.brandmaker.ui.storedetails.StoreDetailsFragment;
import com.algorepublic.brandmaker.ui.tabs.CategoryCheckoutTab;
import com.algorepublic.brandmaker.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


public class AdaptorRVStore extends RecyclerView.Adapter<AdaptorRVStore.ViewHolder> {

    private ArrayList<StoresModel> data;
    private OnListItemClickedListener listener;
    private OnListItemLongClickedListener listenerLong;
    private Context context;
    private ArrayList<StoresModel> items;
    private ItemFilter mFilter = new ItemFilter();
    private Fragment fragment;

    public interface OnListItemClickedListener {
        public void OnReply(int position, String data);
    }

    public interface OnListItemLongClickedListener {
        public void OnReply(int position, String data);
    }

    public AdaptorRVStore(ArrayList<StoresModel> data, Context context, Fragment fragment) {
        this.data = data;
        this.context = context;
        this.fragment = fragment;
        items = data;
    }

    public void setData(ArrayList<StoresModel> data) {
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView;
        itemLayoutView = LayoutInflater.from(context).inflate(R.layout.item_checklist, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        StoresModel storesModel = data.get(position);

        viewHolder.tv_title.setText(storesModel.getStoreName());
        viewHolder.tv_location.setText(storesModel.getStoreLocation());

        viewHolder.button_checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BMApp.db.putBoolean(Constants.CHECK_IN, true);
                BMApp.db.putInt(Constants.CHECKED_IN_STORE_ID, storesModel.getId());
                BMApp.db.putString(Constants.CHECKED_IN_STORE_NAME, storesModel.getStoreName());
                ((MainActivity) context).reload();
//                ((BaseActivity) context).callFragmentWithoutAnimation(R.id.container, CategoryCheckoutTab.getInstance(storesModel.getId(),storesModel.getStoreName()), null);
            }
        });

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) context).callFragmentWithoutAnimation(R.id.container, StoreDetailsFragment.getInstance(storesModel), null);
            }
        });
    }

    public void setOnListItemClickedListener(OnListItemClickedListener listener) {
        this.listener = listener;
    }

    public void setOnListItemLongClickedListener(OnListItemLongClickedListener listenerLong) {
        this.listenerLong = listenerLong;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_location;
        CardView cardView;
        Button button_checkIn;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tv_title = itemLayoutView.findViewById(R.id.tv_title);
            tv_location = itemLayoutView.findViewById(R.id.tv_location);
            cardView = itemLayoutView.findViewById(R.id.cardView);
            button_checkIn = itemLayoutView.findViewById(R.id.button_checkIn);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();

            int count = items.size();
            final List<StoresModel> tempItems = new ArrayList<>(count);

            for (int i = 0; i < count; i++) {
                if (items.get(i) != null) {
                    String name = items.get(i).getStoreName();
                    if (name.toLowerCase().contains(filterString)) {
                        tempItems.add(items.get(i));
                    }
                }
            }

            results.values = tempItems;
            results.count = tempItems.size();

            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data = (ArrayList<StoresModel>) results.values;
            notifyDataSetChanged();
        }
    }

    public Filter getFilter() {
        return mFilter;
    }

}