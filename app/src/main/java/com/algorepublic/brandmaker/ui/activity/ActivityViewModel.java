package com.algorepublic.brandmaker.ui.activity;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By apple on 2019-08-01
 */
public class ActivityViewModel extends AndroidViewModel {


    List<String> myActivities;
    private MutableLiveData<List<String>> muteableActivities;

    public ActivityViewModel(@NonNull Application application) {
        super(application);
        muteableActivities = new MutableLiveData<>();
        myActivities = new ArrayList<>();
    }

    public void setData(){
        for (int i=0;i<10;i++){
            myActivities.add("DATA");
        }
        muteableActivities.setValue(myActivities);
    }


    public MutableLiveData<List<String>> getMyActivityList() {
        return muteableActivities;
    }

}
