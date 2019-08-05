package com.algorepublic.brandmaker.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.algorepublic.brandmaker.R;

import java.util.List;

/**
 * Created By apple on 2019-08-01
 */
public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.RowViewHolder> {

    private List<String> myActivities;
    private Context mContext;

    public ActivityAdapter(Context mContext, List<String> myActivities) {
        this.myActivities = myActivities;
        this.mContext = mContext;
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_activity, parent, false);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RowViewHolder holder, final int position) {
        if(position!=0){
            holder.tv_check_out_time.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return myActivities == null ? 0 : myActivities.size();
    }

    class RowViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_check_in_time;
        TextView tv_check_out_time;
        RowViewHolder(View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_check_in_time=itemView.findViewById(R.id.tv_check_in_time);
            tv_check_out_time=itemView.findViewById(R.id.tv_check_out_time);
        }
    }
}
