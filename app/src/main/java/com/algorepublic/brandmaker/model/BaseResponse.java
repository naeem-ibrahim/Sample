package com.algorepublic.brandmaker.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class BaseResponse implements Serializable {

    @SerializedName("message")
    private String message;
    @SerializedName("success")
    private boolean success;
    @SerializedName("email")
    private String email;
    @SerializedName("data")
    private DataResponse data;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getEmail() { return email; }

    public DataResponse getData() {
        return data;
    }


    public  class DataResponse {
        @SerializedName("user")
        private User user;

        @SerializedName("stores")
        private ArrayList<StoresModel> stores;

        public User getUser() {
            return user;
        }

        public ArrayList<StoresModel> getStores() {
            if (stores==null){
                stores=new ArrayList<>();
            }
            return stores;
        }


    }
}
