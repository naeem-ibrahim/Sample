package com.algorepublic.brandmaker.ui.login;

import com.algorepublic.brandmaker.apirepository.AllAPIRepository;
import com.algorepublic.brandmaker.model.BaseResponse;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class LoginViewModel extends ViewModel {


    private MutableLiveData<BaseResponse> LoginResponseObservable;
    private MutableLiveData<BaseResponse> forgotResponseObservable;
    private MutableLiveData<BaseResponse> resetResponseObservable;
    private MutableLiveData<Boolean> isLoding;
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;

    public MutableLiveData<Boolean> IsPasswordValid;
    public MutableLiveData<Boolean> IsEmailValid;

    public MutableLiveData<Boolean> EnableLogin = new MutableLiveData<>();


    public MutableLiveData<BaseResponse> getLoginResponseObservable() {
        if (LoginResponseObservable == null) {
            LoginResponseObservable = new MutableLiveData<>();
        }
        return LoginResponseObservable;
    }

    public MutableLiveData<BaseResponse> getForgotResponseObservable() {
        if (forgotResponseObservable == null) {
            forgotResponseObservable = new MutableLiveData<>();
        }
        return forgotResponseObservable;
    }

    public MutableLiveData<BaseResponse> getResetResponseObservable() {
        if (resetResponseObservable == null) {
            resetResponseObservable = new MutableLiveData<>();
        }
        return resetResponseObservable;
    }

    public MutableLiveData<Boolean> getIsLoding() {
        if (isLoding == null) {
            isLoding = new MutableLiveData<>();
        }
        return isLoding;
    }

    public MutableLiveData<String> getEmail() {
        if (email == null) {
            email = new MutableLiveData<>();
        }
        return email;
    }

    public MutableLiveData<String> getPassword() {
        if (password == null) {
            password = new MutableLiveData<>();
        }
        return password;
    }

    public MutableLiveData<Boolean> getEnableLogin() {
        if (EnableLogin == null) {
            EnableLogin = new MutableLiveData<>();
        }
        return EnableLogin;
    }

    public MutableLiveData<Boolean> getIsPasswordValid() {
        if (IsPasswordValid == null) {
            IsPasswordValid = new MutableLiveData<>();
        }
        return IsPasswordValid;
    }

    public MutableLiveData<Boolean> getIsEmailValid() {
        if (IsEmailValid == null) {
            IsEmailValid = new MutableLiveData<>();
        }
        return IsEmailValid;
    }


    public void loginAPI() {
        getIsLoding().setValue(true);
        AllAPIRepository.getInstance().Login(email.getValue(), password.getValue()).observeForever(baseResponse -> {
            getIsLoding().setValue(false);
            LoginResponseObservable.setValue(baseResponse);
        });

    }

    public void resetPasswordAPI(String email,String code,String password) {
        getIsLoding().setValue(true);
        AllAPIRepository.getInstance().ResetPassword(email,code,password).observeForever(baseResponse -> {
            getIsLoding().setValue(false);
            resetResponseObservable.setValue(baseResponse);
        });

    }

    public void forgotPasswordAPI(String email) {
        getIsLoding().setValue(true);
        AllAPIRepository.getInstance().ForgotPassword(email).observeForever(baseResponse -> {
            getIsLoding().setValue(false);
            forgotResponseObservable.setValue(baseResponse);
        });

    }
}
