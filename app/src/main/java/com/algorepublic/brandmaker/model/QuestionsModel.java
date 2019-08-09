package com.algorepublic.brandmaker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

/**
 * Created By apple on 2019-08-07
 */
public class QuestionsModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("statement")
    @Expose
    private String statement;
    @SerializedName("questions_type")
    @Expose
    private String questionsType;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("question_type_id")
    @Expose
    private Integer questionTypeId;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("brand_id")
    @Expose
    private Integer brandId;
    @SerializedName("brand_statement_id")
    @Expose
    private Integer brandStatementId;
    @SerializedName("take_pictures")
    @Expose
    private boolean takePictures;

    private File image;
    private String answer;

    public Integer getId() {
        return id;
    }

    public String getStatement() {
        return statement;
    }

    public String getQuestionsType() {
        return questionsType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Integer getQuestionTypeId() {
        return questionTypeId;
    }

    public Integer getPriority() {
        return priority;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public Integer getBrandStatementIdId() {
        return brandStatementId;
    }

    public boolean isTakePictures() {
        return takePictures;
    }


    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
