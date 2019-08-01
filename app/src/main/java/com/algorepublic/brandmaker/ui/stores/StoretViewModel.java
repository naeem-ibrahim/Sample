package com.algorepublic.brandmaker.ui.stores;

import com.algorepublic.brandmaker.apirepository.AllAPIRepository;
import com.algorepublic.brandmaker.model.BaseResponse;
import com.algorepublic.brandmaker.model.StoresModel;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class StoretViewModel extends ViewModel {

    private MutableLiveData<BaseResponse> responseObservable;
    private MutableLiveData<ArrayList<StoresModel>> storeList;
    private MutableLiveData<String> search;
    private MutableLiveData<Boolean> isLoding;

    public MutableLiveData<String> getSearch() {
        if (search == null) {
            search = new MutableLiveData<>();
        }
        return search;
    }

    public MutableLiveData<Boolean> getIsLoding() {
        if (isLoding == null) {
            isLoding = new MutableLiveData<>();
        }
        return isLoding;
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
        getIsLoding().setValue(true);
        AllAPIRepository.getInstance().StoreList().observeForever(baseResponse -> {
            getIsLoding().setValue(false);
            responseObservable.setValue(baseResponse);
        });

    }
}
