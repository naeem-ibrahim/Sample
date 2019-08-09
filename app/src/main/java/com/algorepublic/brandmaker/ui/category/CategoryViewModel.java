package com.algorepublic.brandmaker.ui.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.algorepublic.brandmaker.apirepository.AllAPIRepository;
import com.algorepublic.brandmaker.model.BaseResponse;
import com.algorepublic.brandmaker.model.CategoryModel;
import com.algorepublic.brandmaker.model.StoresModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By apple on 2019-08-02
 */
public class CategoryViewModel extends AndroidViewModel {

    private MutableLiveData<BaseResponse> responseObservable;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        responseObservable = new MutableLiveData<>();
    }

    public MutableLiveData<BaseResponse> getResponseObservable() {
        return responseObservable;
    }

    public void getCategoryApi(int id){
        AllAPIRepository.getInstance().categoryList(id).observeForever(baseResponse -> {
            responseObservable.setValue(baseResponse);
        });
    }


}
