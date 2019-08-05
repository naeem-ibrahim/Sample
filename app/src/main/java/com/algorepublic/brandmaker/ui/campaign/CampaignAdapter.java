package com.algorepublic.brandmaker.ui.campaign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.algorepublic.brandmaker.BaseActivity;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.ui.answers.options.OptionsFragment;
import com.algorepublic.brandmaker.ui.answers.picture.PictureFragment;

import java.util.List;

/**
 * Created By apple on 2019-08-02
 */
public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.RowViewHolder> {

    private List<String> myActivities;
    private Context mContext;

    public CampaignAdapter(Context mContext, List<String> myActivities) {
        this.myActivities = myActivities;
        this.mContext = mContext;
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_campiagn, parent, false);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RowViewHolder holder, final int position) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position%2==0){
                ((BaseActivity) mContext).callFragmentWithoutAnimation(R.id.container, OptionsFragment.getInstance(), null);
                }else {
                    ((BaseActivity) mContext).callFragmentWithoutAnimation(R.id.container, PictureFragment.getInstance(), null);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myActivities == null ? 0 : myActivities.size();
    }

    class RowViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        RowViewHolder(View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
