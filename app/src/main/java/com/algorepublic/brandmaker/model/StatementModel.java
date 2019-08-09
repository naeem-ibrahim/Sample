package com.algorepublic.brandmaker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created By apple on 2019-08-09
 */
public class StatementModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("statement_type")
    @Expose
    private String statementType;
    @SerializedName("brand_id")
    @Expose
    private Integer brandId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("is_complete")
    @Expose
    private boolean isComplete;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatementType() {
        return statementType;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public boolean isComplete() {
        return isComplete;
    }
}
