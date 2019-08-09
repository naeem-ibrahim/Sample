package com.algorepublic.brandmaker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created By apple on 2019-08-07
 */
public class CategoryModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("team_id")
    @Expose
    private Integer teamId;
    @SerializedName("company_id")
    @Expose
    private Object companyId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public Object getCompanyId() {
        return companyId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

}
