package com.algorepublic.brandmaker.ui.activity;

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
import com.algorepublic.brandmaker.databinding.ActivitiesDataBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By apple on 2019-08-01
 */
public class ActivityFragment extends Fragment {
    private ActivitiesDataBinding b;
    private ActivityViewModel viewModel;

    private List<String> myActivities = new ArrayList<>();
    private ActivityAdapter activityAdapter;

    public static ActivityFragment getInstance() {
        ActivityFragment fragment = new ActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_activities, container, false);
        viewModel = ViewModelProviders.of(this).get(ActivityViewModel.class);

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
        activityAdapter = new ActivityAdapter(getContext(), myActivities);
        b.recyclerView.setAdapter(activityAdapter);
    }

    private void updateList(List<String> mList){

        Handler handler= new Handler();
        handler.postDelayed(() -> {
            myActivities.addAll(mList);
            activityAdapter.notifyDataSetChanged();
        },2000);

    }

}
