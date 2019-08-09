package com.algorepublic.brandmaker.apirepository;


import android.util.Log;

import com.algorepublic.brandmaker.model.BaseResponse;
import com.algorepublic.brandmaker.model.QuestionsModel;
import com.algorepublic.brandmaker.network.ApiClient;
import org.json.JSONObject;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

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

    public MutableLiveData<BaseResponse> categoryList(int storeID) {
        final MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        ApiClient.getInstance().getCategories(storeID,new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
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

    public MutableLiveData<BaseResponse> brandsList(int categoryID) {
        final MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        ApiClient.getInstance().getBrands(categoryID,new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
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

    public MutableLiveData<BaseResponse> statementList(int brandID) {
        final MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        ApiClient.getInstance().getStatements(brandID,new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
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

    public MutableLiveData<BaseResponse> questionsList(int brandStatementID) {
        final MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        ApiClient.getInstance().getQuestions(brandStatementID,new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
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

    public MutableLiveData<BaseResponse> uploadAnswers(int brandStatementID, ArrayList<QuestionsModel> userAnswers) {
        final MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        ApiClient.getInstance().uploadAnswers(brandStatementID,userAnswers,new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
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
