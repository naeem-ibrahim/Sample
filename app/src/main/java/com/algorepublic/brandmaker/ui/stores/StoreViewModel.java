package com.algorepublic.brandmaker.ui.stores;

import com.algorepublic.brandmaker.apirepository.AllAPIRepository;
import com.algorepublic.brandmaker.model.BaseResponse;
import com.algorepublic.brandmaker.model.StoresModel;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class StoreViewModel extends ViewModel {

    private MutableLiveData<BaseResponse> responseObservable;
    private MutableLiveData<ArrayList<StoresModel>> storeList;
    private MutableLiveData<String> search;
    private MutableLiveData<Boolean> isLoading;

    public MutableLiveData<String> getSearch() {
        if (search == null) {
            search = new MutableLiveData<>();
        }
        return search;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }

    public MutableLiveData<BaseResponse> getResponseObservable() {
        if (responseObservable == null) {
            responseObservable = new MutableLiveData<>();
        }
        return responseObservable;
    }

    public MutableLiveData<ArrayList<StoresModel>> getStoreList() {
        if (storeList == null) {
            storeList = new MutableLiveData<>();
        }
        return storeList;
    }

    public void storeListAPI() {
        getIsLoading().setValue(true);
        AllAPIRepository.getInstance().StoreList().observeForever(baseResponse -> {
            getIsLoading().setValue(false);
            responseObservable.setValue(baseResponse);
        });
    }
}
