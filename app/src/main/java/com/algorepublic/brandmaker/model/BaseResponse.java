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


    public class DataResponse {
        @SerializedName("user")
        private User user;

        @SerializedName("stores")
        private ArrayList<StoresModel> stores;

        @SerializedName("categories")
        private ArrayList<CategoryModel> categories;

        @SerializedName("brands")
        private ArrayList<BrandModel> brands;

        @SerializedName("questions")
        private ArrayList<QuestionsModel> questionsModels;

        @SerializedName("statements")
        private ArrayList<StatementModel> statementModels;

        public User getUser() {
            return user;
        }

        public ArrayList<StoresModel> getStores() {
            if (stores==null){
                stores=new ArrayList<>();
            }
            return stores;
        }

        public ArrayList<CategoryModel> getCategories() {
            if (categories==null){
                categories=new ArrayList<>();
            }
            return categories;
        }

        public ArrayList<BrandModel> getBrands() {
            if (brands==null){
                brands=new ArrayList<>();
            }
            return brands;
        }

        public ArrayList<QuestionsModel> getQuestions() {
            if (questionsModels==null){
                questionsModels=new ArrayList<>();
            }
            return questionsModels;
        }

        public ArrayList<StatementModel> getStatements() {
            if (statementModels==null){
                statementModels=new ArrayList<>();
            }
            return statementModels;
        }
    }
}
