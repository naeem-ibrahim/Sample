package com.algorepublic.brandmaker.network;




import com.algorepublic.brandmaker.BMApp;
import com.algorepublic.brandmaker.model.BaseResponse;
import com.algorepublic.brandmaker.utils.Constants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.algorepublic.brandmaker.utils.Helper.toRequestBody;


public class ApiClient {
    private static ApiClient instance;
    private static final String BASE_URL = Constants.BASE_URL;
    private static Retrofit retrofit = null;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private ApiClient() {
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public void Login(String email, String password, Callback<BaseResponse> callback) {
        Map<String, String> hash = new HashMap<>();
        hash.put("user[email]", email);
        hash.put("user[password]", password);
        Call<BaseResponse> call = apiService.Login(hash);
        call.enqueue(callback);
    }

    public void ForgotPassword(String email, Callback<BaseResponse> callback) {
        Map<String, String> hash = new HashMap<>();
        hash.put("email", email);
        Call<BaseResponse> call = apiService.ForgotPassword(hash);
        call.enqueue(callback);
    }

    public void ResetPassword(String email, String code,String password, Callback<BaseResponse> callback) {
        Map<String, String> hash = new HashMap<>();
        hash.put("email",email);
        hash.put("code", code);
        hash.put("password", password);
        Call<BaseResponse> call = apiService.ResetPassword(hash);
        call.enqueue(callback);
    }

    public void StoreList(Callback<BaseResponse> callback) {
        Call<BaseResponse> call = apiService.StoresList(BMApp.db.getUserObj().getToken());
        call.enqueue(callback);
    }




}