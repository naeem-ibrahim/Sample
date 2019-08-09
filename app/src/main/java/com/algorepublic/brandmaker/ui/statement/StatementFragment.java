package com.algorepublic.brandmaker.ui.statement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.FragmentCategoryBinding;
import com.algorepublic.brandmaker.model.StatementModel;
import com.algorepublic.brandmaker.utils.Helper;


import java.util.ArrayList;
import java.util.List;

/**
 * Created By apple on 2019-08-02
 */
public class StatementFragment extends Fragment {
    private FragmentCategoryBinding binding;
    private StatementViewModel viewModel;

    private List<StatementModel> statementModelList = new ArrayList<>();
    private StatementAdapter statementAdapter;
    private int brandID=0;

    public static StatementFragment getInstance(int brandID) {
        StatementFragment fragment = new StatementFragment();
        Bundle args = new Bundle();
        args.putInt("BrandID",brandID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        viewModel = ViewModelProviders.of(this).get(StatementViewModel.class);

        brandID=getArguments().getInt("BrandID");

        setRecyclerView();

        viewModel.getCampaignObservable().observe(this, baseResponse -> {
            binding.progressBar.setVisibility(View.GONE);
            if (baseResponse != null) {
                if (baseResponse.isSuccess() && baseResponse.getData() != null) {
                    if (baseResponse.getData().getStatements().size() > 0) {
                        statementModelList.clear();
                        statementModelList.addAll(baseResponse.getData().getStatements());
                    } else {
                        binding.tvEmpty.setText("No Statement for this brand");
                        binding.tvEmpty.setVisibility(View.VISIBLE);
                    }
                    statementAdapter.notifyDataSetChanged();
                } else {
                    Helper.snackBarWithAction(binding.getRoot(), getActivity(), baseResponse.getMessage());
                }
            }
        });


        viewModel.getStatementsApi(brandID);



        return binding.getRoot();
    }

    private void setRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setNestedScrollingEnabled(false);
        statementModelList = new ArrayList<>();
        statementAdapter = new StatementAdapter(getContext(), statementModelList);
        binding.recyclerView.setAdapter(statementAdapter);
    }

}
