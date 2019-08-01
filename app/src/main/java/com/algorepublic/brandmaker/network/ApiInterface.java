package com.algorepublic.brandmaker.network;


import com.algorepublic.brandmaker.model.BaseResponse;

import java.util.Map;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<BaseResponse> Login(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/api/v1/forgot")
    Call<BaseResponse> ForgotPassword(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/api/v1/reset")
    Call<BaseResponse> ResetPassword(@FieldMap Map<String, String> params);

    @GET("/api/v1/stores")
    Call<BaseResponse> StoresList(@Header("Authorization") String token);

}