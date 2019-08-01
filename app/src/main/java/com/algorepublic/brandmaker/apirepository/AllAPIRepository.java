package com.algorepublic.brandmaker.apirepository;


import android.util.Log;

import com.algorepublic.brandmaker.model.BaseResponse;
import com.algorepublic.brandmaker.network.ApiClient;
import org.json.JSONObject;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllAPIRepository {
    private static final String TAG = "AllAPIRepository";

    private AllAPIRepository(){}

    private static class SingletonHelper
    {
        private static final AllAPIRepository INSTANCE = new AllAPIRepository();
    }

    public static AllAPIRepository getInstance()
    {
        return SingletonHelper.INSTANCE;
    }

    public MutableLiveData<BaseResponse> Login(String email,String password) {
        final MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        ApiClient.getInstance().Login(email,password,new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.e(TAG, "onResponse :" + response);
                data.setValue(response.body());

            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e(TAG, "onFailure :" + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }


    public MutableLiveData<BaseResponse> ForgotPassword(String email) {
        final MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        ApiClient.getInstance().ForgotPassword(email,new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.e(TAG, "onResponse :" + response);
                if (response.isSuccessful()){
                    data.setValue(response.body());
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        BaseResponse f=new BaseResponse();
                        f.setMessage(jObjError.getString("message"));
                        f.setSuccess(false);
                        data.setValue(f);
                    } catch (Exception e) {
                        e.printStackTrace();
                        data.setValue(null);
                    }

                }

            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e(TAG, "onFailure :" + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<BaseResponse> ResetPassword(String email,String code,String password) {
        final MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        ApiClient.getInstance().ResetPassword(email,code,password,new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.e(TAG, "onResponse :" + response);
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        BaseResponse f=new BaseResponse();
                        f.setMessage(jObjError.getString("message"));
                        f.setSuccess(false);
                        data.setValue(f);
                    } catch (Exception e) {
                        e.printStackTrace();
                        data.setValue(null);
                    }

                }
            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e(TAG, "onFailure :" + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<BaseResponse> StoreList() {
        final MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        ApiClient.getInstance().StoreList(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.e(TAG, "onResponse :" + response);
                data.setValue(response.body());
            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e(TAG, "onFailure :" + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }


}
