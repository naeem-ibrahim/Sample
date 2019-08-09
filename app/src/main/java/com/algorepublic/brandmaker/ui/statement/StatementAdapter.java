package com.algorepublic.brandmaker.ui.statement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.algorepublic.brandmaker.BaseActivity;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.model.StatementModel;
import com.algorepublic.brandmaker.ui.answers.options.OptionsFragment;

import java.util.List;

/**
 * Created By apple on 2019-08-02
 */
public class StatementAdapter extends RecyclerView.Adapter<StatementAdapter.RowViewHolder> {

    private List<StatementModel> statementModelList;
    private Context mContext;

    public StatementAdapter(Context mContext, List<StatementModel> statementModelList) {
        this.statementModelList = statementModelList;
        this.mContext = mContext;
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_campiagn, parent, false);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RowViewHolder holder, final int position) {

        StatementModel statementModel = statementModelList.get(position);
        holder.tvQuestion.setText(statementModel.getTitle());
        holder.completedCheckbox.setChecked(statementModel.isComplete());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!statementModel.isComplete()) {
                    ((BaseActivity) mContext).callFragmentWithoutAnimation(R.id.container, OptionsFragment.getInstance(statementModel.getId()), null);
//                ((BaseActivity) mContext).callFragmentWithoutAnimation(R.id.container, PictureFragment.getInstance(), null);
                }else {
                    Toast.makeText(mContext,"Tasks Already Complete.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return statementModelList == null ? 0 : statementModelList.size();
    }

    class RowViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvQuestion;
        CheckBox completedCheckbox;

        RowViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            completedCheckbox = itemView.findViewById(R.id.complete_checkbox);
        }
    }
}
