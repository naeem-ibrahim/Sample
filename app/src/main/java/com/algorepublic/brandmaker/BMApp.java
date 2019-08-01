package com.algorepublic.brandmaker;

import android.app.Application;
import android.os.Build;
;

import com.algorepublic.brandmaker.utils.TinyDB;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class BMApp extends Application {
    public static BMApp instance;
    public static TinyDB db;



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db=new TinyDB(this);

    }






}
