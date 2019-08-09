package com.algorepublic.brandmaker.network;


import com.algorepublic.brandmaker.model.BaseResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiInterface {



    @GET("/api/v1/stores")
    Call<BaseResponse> StoresList(@Header("Authorization") String token);


    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<BaseResponse> Login(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/api/v1/forgot")
    Call<BaseResponse> ForgotPassword(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/api/v1/reset")
    Call<BaseResponse> ResetPassword(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("/api/v1/categories")
    Call<BaseResponse> getCategories(@Header("Authorization") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/api/v1/brands")
    Call<BaseResponse> getBrands(@Header("Authorization") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/api/v1/brand_statements")
    Call<BaseResponse> getStatements(@Header("Authorization") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/api/v1/questions/get_questions")
    Call<BaseResponse> getQuestions(@Header("Authorization") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/api/v1/answers")
    Call<BaseResponse> answers(@Header("Authorization") String token, @FieldMap Map<String, Object> params);


    @Multipart
    @POST("api/v1/answers")
    Call<BaseResponse> answerWithPicture(@Header("Authorization") String token,
                                       @Query("id") int questionID,
                                       @Query("id") int questionTypeID,
                                       @Query("answer") String answer,
                                       @Part MultipartBody.Part[] images);

    @POST("api/v1/answers")
    Call<BaseResponse> answerWithoutPicture(@Header("Authorization") String token,
                                       @Query("id") int questionID,
                                       @Query("id") int questionTypeID,
                                       @Query("answer") String answer);

}