package com.algorepublic.brandmaker.ui.stores;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.StoreDataBinding;
import com.algorepublic.brandmaker.model.StoresModel;
import com.algorepublic.brandmaker.ui.dashboard.MainActivity;
import com.algorepublic.brandmaker.utils.Constants;
import com.algorepublic.brandmaker.utils.GPSListener;
import com.algorepublic.brandmaker.utils.GpsUtils;
import com.algorepublic.brandmaker.utils.Helper;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


public class StoreFragment extends Fragment implements GPSListener {
    private StoreDataBinding binding;
    private AdaptorRVStore adaptor;
    private StoreViewModel viewModel;

    private double wayLatitude = 0.0, wayLongitude = 0.0;

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
        getCurrentLocation();
        ((MainActivity) getContext()).setListener(this);
        return binding.getRoot();
    }

    private void initBining(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false);
        viewModel = ViewModelProviders.of(this).get(StoreViewModel.class);
        binding.setStore(viewModel);
        setRecyclerView();

        viewModel.getResponseObservable().observe(this, baseResponse -> {
            binding.progressBar.setVisibility(View.GONE);
            if (baseResponse != null) {
                if (baseResponse.isSuccess()) {
                    if (adaptor != null) {
                        adaptor.setData(baseResponse.getData().getStores());
                    }
                } else {
                    Helper.snackBarWithAction(binding.getRoot(), getActivity(), baseResponse.getMessage());
                }
            }
        });

        viewModel.getSearch().observe(this, s ->
        {
            if (adaptor != null) {
                adaptor.getFilter().filter(s);
            }
        });
    }


    private void setRecyclerView() {
        adaptor = new AdaptorRVStore(new ArrayList<>(), getActivity(), this);
        layoutManager = new GridLayoutManager(getActivity(), 2);
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
                if (lastVisibleItem > 15) {
                    YoYo.with(Techniques.SlideOutUp)
                            .duration(200)
                            .repeat(0)
                            .onEnd(animator -> binding.searchLayout.setVisibility(View.GONE))
                            .playOn(binding.searchLayout);
                } else {
                    if (binding.searchLayout.getVisibility() == View.GONE) {
                        YoYo.with(Techniques.SlideInDown)
                                .duration(200)
                                .repeat(0)
                                .onEnd(animator -> binding.searchLayout.setVisibility(View.VISIBLE))
                                .playOn(binding.searchLayout);
                    }
                }
            }
        });
    }


    /***************Get User Location***************/

    private FusedLocationProviderClient mFusedLocationClient;
    private boolean isGPS = false;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private void getCurrentLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        locationCallback();

        new GpsUtils(getContext()).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });

        if (!isGPS) {
            Toast.makeText(getContext(), "Please turn on GPS", Toast.LENGTH_SHORT).show();
            return;
        }
        getLocation();
    }

    private void locationCallback(){
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        Log.e("Location",String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));
                        if (mFusedLocationClient != null) {
                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                        }
                    }
                }
            }
        };
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.LOCATION_REQUEST);
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {
                if (location != null) {
                    wayLatitude = location.getLatitude();
                    wayLongitude = location.getLongitude();
                    Log.e("Location",String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));
                }else {
                    mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                }
            });

        }
    }

    @Override
    public void onEnable(boolean isGPSEnable) {
        isGPS = isGPSEnable;
        if (!isGPS) {
            Toast.makeText(getContext(), "Please turn on GPS", Toast.LENGTH_SHORT).show();
            return;
        }
        getLocation();
    }

    /***************Get User Location***************/

}
