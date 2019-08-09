package com.algorepublic.brandmaker.ui.brand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.algorepublic.brandmaker.BaseActivity;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.model.BrandModel;
import com.algorepublic.brandmaker.ui.tabs.CampaignTasksTab;

import java.util.List;

/**
 * Created By apple on 2019-08-02
 */
public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.RowViewHolder> {

    private List<BrandModel> brandsArrayList;
    private Context mContext;

    public BrandAdapter(Context mContext, List<BrandModel> brandsArrayList) {
        this.brandsArrayList = brandsArrayList;
        this.mContext = mContext;
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RowViewHolder holder, final int position) {
        BrandModel brand= brandsArrayList.get(position);
        holder.tv_title.setText(brand.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) mContext).callFragmentWithoutAnimation(R.id.container, CampaignTasksTab.getInstance(brand.getId(),brand.getName()), null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brandsArrayList == null ? 0 : brandsArrayList.size();
    }

    class RowViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        CardView cardView;
        RowViewHolder(View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
