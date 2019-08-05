package com.algorepublic.brandmaker.ui.stores;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.StoreDataBinding;
import com.algorepublic.brandmaker.utils.Helper;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


public class StoreFragment extends Fragment {
    private StoreDataBinding binding;
    private AdaptorRVStore adaptor;
    private StoreViewModel viewModel;

    int totalItemCount, lastVisibleItem;
    GridLayoutManager layoutManager;

    public static StoreFragment getInstance() {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initBining(inflater, container);
        viewModel.storeListAPI();
        return binding.getRoot();
    }

    private void initBining(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false);
        viewModel = ViewModelProviders.of(this).get(StoreViewModel.class);
        binding.setStore(viewModel);


        viewModel.getResponseObservable().observe(this, baseResponse -> {
            if (baseResponse != null) {
                if (baseResponse.isSuccess()) {
                    viewModel.getStoreList().setValue(baseResponse.getData().getStores());
                } else {
                    Helper.snackBarWithAction(binding.getRoot(), getActivity(), baseResponse.getMessage());
                }
            }

        });

        viewModel.getStoreList().observe(this, storesModel -> {
            setRecyclerView();
        });

        viewModel.getSearch().observe(this, s ->
        {
            if (adaptor != null) {
                adaptor.getFilter().filter(s);
            }
        });
    }


    private void setRecyclerView() {
        adaptor = new AdaptorRVStore(viewModel.getStoreList().getValue(), getActivity(),this);
        layoutManager=new GridLayoutManager(getActivity(), 2);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adaptor);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adaptor);
        binding.recyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // total number of items in the data set held by the adapter
                totalItemCount = layoutManager.getItemCount();

                //adapter position of the first visible view.
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItem>15){
                    YoYo.with(Techniques.SlideOutUp)
                            .duration(200)
                            .repeat(0)
                            .onEnd(animator -> binding.clSearch.setVisibility(View.GONE))
                            .playOn(binding.clSearch);
                }
                else {
                    if(binding.clSearch.getVisibility()==View.GONE) {
                        YoYo.with(Techniques.SlideInDown)
                                .duration(200)
                                .repeat(0)
                                .onEnd(animator -> binding.clSearch.setVisibility(View.VISIBLE))
                                .playOn(binding.clSearch);
                    }
                }
            }
        });
    }
}
