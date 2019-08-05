package com.algorepublic.brandmaker.ui.campaign;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.FragmentCategoryBinding;


import java.util.ArrayList;
import java.util.List;

/**
 * Created By apple on 2019-08-02
 */
public class CampaignFragment extends Fragment {
    private FragmentCategoryBinding b;
    private CampaignViewModel viewModel;

    private List<String> myActivities = new ArrayList<>();
    private CampaignAdapter campaignAdapter;

    public static CampaignFragment getInstance() {
        CampaignFragment fragment = new CampaignFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        viewModel = ViewModelProviders.of(this).get(CampaignViewModel.class);

        setRecyclerView();

        viewModel.getMyActivityList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> mList) {
                updateList(mList);
            }
        });

        viewModel.setData();

        return b.getRoot();
    }

    private void setRecyclerView() {
        b.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        b.recyclerView.setNestedScrollingEnabled(false);
        myActivities = new ArrayList<>();
        campaignAdapter = new CampaignAdapter(getContext(), myActivities);
        b.recyclerView.setAdapter(campaignAdapter);
    }

    private void updateList(List<String> mList){
        Handler handler= new Handler();
        handler.postDelayed(() -> {
            myActivities.addAll(mList);
            campaignAdapter.notifyDataSetChanged();
        },1000);
    }

}
