package com.algorepublic.brandmaker.ui.stores;


import android.animation.Animator;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


public class StoreFragment extends Fragment {
    private static StoreFragment fragment;
    private StoreDataBinding b;
    private AdaptorRVStore adaptor;
    private StoretViewModel vm;

    int totalItemCount, lastVisibleItem;
    GridLayoutManager layoutManager;
    private int visibleThreshold = 5;

    public static StoreFragment getInstance() {
        if (fragment == null) {
            fragment = new StoreFragment();
        }
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initBining(inflater, container);
        vm.storeListAPI();
        return b.getRoot();
    }

    private void initBining(LayoutInflater inflater, ViewGroup container) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false);
        vm = ViewModelProviders.of(this).get(StoretViewModel.class);
        b.setStore(vm);


        vm.getResponseObservable().observe(this, baseResponse -> {
            if (baseResponse != null) {
                if (baseResponse.isSuccess()) {
                    vm.getStoreList().setValue(baseResponse.getData().getStores());
                } else {
                    Helper.snackBarWithAction(b.getRoot(), getActivity(), baseResponse.getMessage());
                }
            }

        });

        vm.getStoreList().observe(this, storesModel -> {
            setRecyclerView();
        });

        vm.getSearch().observe(this, s ->
        {
            if (adaptor != null) {
                adaptor.getFilter().filter(s);
            }
        });
    }


    private void setRecyclerView() {
        adaptor = new AdaptorRVStore(vm.getStoreList().getValue(), getActivity());
        layoutManager=new GridLayoutManager(getActivity(), 2);
        b.recyclerView.setLayoutManager(layoutManager);
        b.recyclerView.setAdapter(adaptor);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adaptor);
        b.recyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
        b.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            .onEnd(animator -> b.clSearch.setVisibility(View.GONE))
                            .playOn(b.clSearch);
                }
                else {
                    if(b.clSearch.getVisibility()==View.GONE) {
                        YoYo.with(Techniques.SlideInDown)
                                .duration(200)
                                .repeat(0)
                                .onEnd(animator -> b.clSearch.setVisibility(View.VISIBLE))
                                .playOn(b.clSearch);
                    }
                }
            }
        });
    }
}
