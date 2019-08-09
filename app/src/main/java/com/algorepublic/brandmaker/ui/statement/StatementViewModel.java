package com.algorepublic.brandmaker.ui.statement;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.algorepublic.brandmaker.apirepository.AllAPIRepository;
import com.algorepublic.brandmaker.model.BaseResponse;

/**
 * Created By apple on 2019-08-02
 */
public class StatementViewModel extends AndroidViewModel {

    private MutableLiveData<BaseResponse> campaignObservable;

    public StatementViewModel(@NonNull Application application) {
        super(application);
        campaignObservable = new MutableLiveData<>();
    }

    public MutableLiveData<BaseResponse> getCampaignObservable() {
        return campaignObservable;
    }

    public void getStatementsApi(int brandID){
        AllAPIRepository.getInstance().statementList(brandID).observeForever(baseResponse -> {
            campaignObservable.setValue(baseResponse);
        });
    }

}
