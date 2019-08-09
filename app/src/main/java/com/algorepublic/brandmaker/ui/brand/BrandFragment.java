package com.algorepublic.brandmaker.ui.brand;

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
import com.algorepublic.brandmaker.model.BaseResponse;
import com.algorepublic.brandmaker.model.BrandModel;
import com.algorepublic.brandmaker.ui.category.CategoryAdapter;
import com.algorepublic.brandmaker.ui.category.CategoryFragment;
import com.algorepublic.brandmaker.ui.category.CategoryViewModel;
import com.algorepublic.brandmaker.utils.Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By apple on 2019-08-02
 */
public class BrandFragment extends Fragment {
    private FragmentCategoryBinding binding;
    private BrandViewModel viewModel;

    private List<BrandModel> brandsArrayList = new ArrayList<>();
    private BrandAdapter brandAdapter;
    private int categoryId;

    public static BrandFragment getInstance(int categoryId) {
        BrandFragment fragment = new BrandFragment();
        Bundle args = new Bundle();
        args.putInt("CategoryID",categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        viewModel = ViewModelProviders.of(this).get(BrandViewModel.class);

        categoryId=getArguments().getInt("CategoryID");
        setRecyclerView();

        viewModel.getResponseObservable().observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                binding.progressBar.setVisibility(View.GONE);
                if (baseResponse != null) {
                    if (baseResponse.isSuccess() && baseResponse.getData() != null) {
                        if (baseResponse.getData().getBrands().size() > 0) {
                            brandsArrayList.clear();
                            brandsArrayList.addAll(baseResponse.getData().getBrands());
                        } else {
                            binding.tvEmpty.setText("No Brand Found");
                            binding.tvEmpty.setVisibility(View.VISIBLE);
                        }
                        brandAdapter.notifyDataSetChanged();
                    } else {
                        Helper.snackBarWithAction(binding.getRoot(), getActivity(), baseResponse.getMessage());
                    }
                }
            }
        });

        viewModel.getBrandsApi(categoryId);

        return binding.getRoot();
    }

    private void setRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setNestedScrollingEnabled(false);
        brandsArrayList = new ArrayList<>();
        brandAdapter = new BrandAdapter(getContext(), brandsArrayList);
        binding.recyclerView.setAdapter(brandAdapter);
    }
}
