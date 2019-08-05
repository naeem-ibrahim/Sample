package com.algorepublic.brandmaker.ui.category;

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
public class CategoryFragment extends Fragment {
    private FragmentCategoryBinding b;
    private CategoryViewModel viewModel;

    private List<String> myActivities = new ArrayList<>();
    private CategoryAdapter categoryAdapter;

    public static CategoryFragment getInstance() {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

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
        categoryAdapter = new CategoryAdapter(getContext(), myActivities);
        b.recyclerView.setAdapter(categoryAdapter);
    }

    private void updateList(List<String> mList){

        Handler handler= new Handler();
        handler.postDelayed(() -> {
            myActivities.addAll(mList);
            categoryAdapter.notifyDataSetChanged();
        },1000);

    }

}
