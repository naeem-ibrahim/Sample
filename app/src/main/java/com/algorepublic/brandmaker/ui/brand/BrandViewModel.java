package com.algorepublic.brandmaker.ui.brand;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.algorepublic.brandmaker.apirepository.AllAPIRepository;
import com.algorepublic.brandmaker.model.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By apple on 2019-08-02
 */
public class BrandViewModel extends AndroidViewModel {


    private MutableLiveData<BaseResponse> responseObservable;

    public BrandViewModel(@NonNull Application application) {
        super(application);
        responseObservable = new MutableLiveData<>();
    }

    public MutableLiveData<BaseResponse> getResponseObservable() {
        return responseObservable;
    }

    public void getBrandsApi(int id){
        AllAPIRepository.getInstance().brandsList(id).observeForever(baseResponse -> {
            responseObservable.setValue(baseResponse);
        });
    }


}
