package com.algorepublic.brandmaker.ui.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.algorepublic.brandmaker.BaseActivity;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.model.CategoryModel;
import com.algorepublic.brandmaker.ui.tabs.BrandsCheckoutTab;
import com.algorepublic.brandmaker.ui.tabs.CategoryCheckoutTab;

import java.util.List;

/**
 * Created By apple on 2019-08-02
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RowViewHolder> {

    private List<CategoryModel> categories;
    private Context mContext;

    public CategoryAdapter(Context mContext, List<CategoryModel> categories) {
        this.categories = categories;
        this.mContext = mContext;
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RowViewHolder holder, final int position) {

        CategoryModel categoryModel=categories.get(position);
        holder.tv_title.setText(categoryModel.getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) mContext).callFragmentWithoutAnimation(R.id.container, BrandsCheckoutTab.getInstance(categoryModel.getId(),categoryModel.getStoreId(),categoryModel.getName()), null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
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
