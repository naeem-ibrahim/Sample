package com.algorepublic.brandmaker.network;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.algorepublic.brandmaker.BMApp;
import com.algorepublic.brandmaker.model.BaseResponse;
import com.algorepublic.brandmaker.model.QuestionsModel;
import com.algorepublic.brandmaker.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private ApiClient() {
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    final private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();

    private static Retrofit getClient() {
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

    public void ResetPassword(String email, String code, String password, Callback<BaseResponse> callback) {
        Map<String, String> hash = new HashMap<>();
        hash.put("email", email);
        hash.put("code", code);
        hash.put("password", password);
        Call<BaseResponse> call = apiService.ResetPassword(hash);
        call.enqueue(callback);
    }

    public void StoreList(Callback<BaseResponse> callback) {
        Call<BaseResponse> call = apiService.StoresList(BMApp.db.getUserObj().getToken());
        call.enqueue(callback);
    }


    public void getCategories(int storeID, Callback<BaseResponse> callback) {
        Map<String, Object> hash = new HashMap<>();
        hash.put("store[id]", storeID);
        Call<BaseResponse> call = apiService.getCategories(BMApp.db.getUserObj().getToken(), hash);
        call.enqueue(callback);
    }

    public void getBrands(int categoryID, Callback<BaseResponse> callback) {
        Map<String, Object> hash = new HashMap<>();
        hash.put("category[id]", categoryID);
        Call<BaseResponse> call = apiService.getBrands(BMApp.db.getUserObj().getToken(), hash);
        call.enqueue(callback);
    }

    public void getStatements(int brandID, Callback<BaseResponse> callback) {
        Map<String, Object> hash = new HashMap<>();
        hash.put("brand[id]", brandID);
        Call<BaseResponse> call = apiService.getStatements(BMApp.db.getUserObj().getToken(), hash);
        call.enqueue(callback);
    }

    public void getQuestions(int brandStatementID, Callback<BaseResponse> callback) {
        Map<String, Object> hash = new HashMap<>();
        hash.put("brand_statement[id]", brandStatementID);
        Call<BaseResponse> call = apiService.getQuestions(BMApp.db.getUserObj().getToken(), hash);
        call.enqueue(callback);
    }


    public void answer(int questionID, int questionTypeID, String answer, List<File> files, Callback<BaseResponse> callback) {
        if (files.size() > 0) {
            MultipartBody.Part[] images = new MultipartBody.Part[files.size()];

            for (int index = 0; index < files.size(); index++) {
                RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), files.get(index));
                images[index] = MultipartBody.Part.createFormData("avatar", files.get(index).getName(), surveyBody);
            }

            Call<BaseResponse> call = apiService.answerWithPicture(BMApp.db.getUserObj().getToken(),questionID,questionTypeID,answer,images);
            call.enqueue(callback);
        } else {
            Call<BaseResponse> call = apiService.answerWithoutPicture(BMApp.db.getUserObj().getToken(),questionID,questionTypeID,answer);
            call.enqueue(callback);
        }

    }


    public void uploadAnswers(int brandStatementID, ArrayList<QuestionsModel> userAnswers, Callback<BaseResponse> callback) {
        JSONArray array = new JSONArray();
        JSONObject obj;
        for (int p = 0; p < userAnswers.size(); p++) {
            obj = new JSONObject();
            try {
                obj.put("id", userAnswers.get(p).getId());
                obj.put("answer",userAnswers.get(p).getAnswer());
//                RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), userAnswers.get(p).getImage());
//                MultipartBody.Part image = MultipartBody.Part.createFormData("avatar", userAnswers.get(p).getImage().getName(), surveyBody);
//                obj.put("avatar",image);
                if(userAnswers.get(p).getImage()!=null) {
                    obj.put("avatar", base64(userAnswers.get(p).getImage()));
                }
                array.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> hash = new HashMap<>();
        hash.put("brand_statement[id]", brandStatementID);
        hash.put("answers", array);
        Call<BaseResponse> call = apiService.answers(BMApp.db.getUserObj().getToken(), hash);
        call.enqueue(callback);
    }

    private String base64(File image){
        String base64Image=null;
        if (image.exists() && image.length() > 0) {
            Bitmap bm = BitmapFactory.decodeFile(image.getPath());
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 70, bOut);
            base64Image = Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);
        }

        return base64Image;
    }

}