package com.algorepublic.brandmaker.ui.answers.options;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.algorepublic.brandmaker.apirepository.AllAPIRepository;
import com.algorepublic.brandmaker.model.BaseResponse;
import com.algorepublic.brandmaker.model.QuestionsModel;

import java.util.ArrayList;

/**
 * Created By apple on 2019-08-02
 */
public class OptionViewModel extends AndroidViewModel {

    private MutableLiveData<BaseResponse> questionsObservable;
    private MutableLiveData<BaseResponse> uploadingAnswersObservable;

    public OptionViewModel(@NonNull Application application) {
        super(application);
        questionsObservable = new MutableLiveData<>();
        uploadingAnswersObservable = new MutableLiveData<>();
    }

    public MutableLiveData<BaseResponse> getQuestionObservable() {
        return questionsObservable;
    }

    public MutableLiveData<BaseResponse> getUploadingAnswersObservable() {
        return uploadingAnswersObservable;
    }

    public void getQuestionsApi(int brandStatementID){
        AllAPIRepository.getInstance().questionsList(brandStatementID).observeForever(baseResponse -> {
            questionsObservable.setValue(baseResponse);
        });
    }

    public void uploadAnswers(int brandStatementID, ArrayList<QuestionsModel> userAnswers){
        AllAPIRepository.getInstance().uploadAnswers(brandStatementID,userAnswers).observeForever(baseResponse -> {
            uploadingAnswersObservable.setValue(baseResponse);
        });
    }

}
