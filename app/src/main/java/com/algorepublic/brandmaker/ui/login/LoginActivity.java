package com.algorepublic.brandmaker.ui.login;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.algorepublic.brandmaker.BMApp;
import com.algorepublic.brandmaker.MainActivity;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.LoginDataBinding;
import com.algorepublic.brandmaker.model.BaseResponse;
import com.algorepublic.brandmaker.utils.Helper;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class LoginActivity extends AppCompatActivity {
    private LoginDataBinding b;
    private LoginViewModel vm;

    private Dialog dialog;
    private Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBining();
        clicks();
    }

    private void clicks(){
        b.btnLogin.setOnClickListener(v -> vm.loginAPI());
        b.forgetTxt.setOnClickListener(view -> {
            dialogForgotPassword();
        });
    }

    private void initBining() {
        b = DataBindingUtil.setContentView(this, R.layout.activity_login);
        vm = ViewModelProviders.of(this).get(LoginViewModel.class);
        b.setLogin(vm);


        vm.getLoginResponseObservable().observe(this, baseResponse -> {
            if (baseResponse!=null){
                if (baseResponse.isSuccess()){
                    BMApp.db.setUserObg(baseResponse.getData().getUser());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else {
                    YoYo.with(Techniques.Tada)
                            .duration(500)
                            .repeat(0)
                            .playOn(b.constraintLayout);
                    Helper.snackBarWithAction(getWindow().getDecorView().getRootView(),LoginActivity.this,baseResponse.getMessage());
                }



            }

        });

        vm.getForgotResponseObservable().observe(this,baseResponse -> {
            if (baseResponse!=null){
                if (baseResponse.isSuccess()){
                    d.dismiss();
                    dialogResetPassword(baseResponse.getEmail());
                    Helper.showSnack(getWindow().getDecorView().getRootView(),LoginActivity.this,baseResponse.getMessage());
                }else {
                    Helper.snackBarWithAction(getWindow().getDecorView().getRootView(),LoginActivity.this,baseResponse.getMessage());
                }
            }
        });

        vm.getResetResponseObservable().observe(this,baseResponse -> {
            if (baseResponse!=null) {
                if (baseResponse.isSuccess()) {
                    d.dismiss();
                }
                Helper.snackBarWithAction(getWindow().getDecorView().getRootView(), LoginActivity.this, baseResponse.getMessage());

            }
        });

        vm.getIsLoding().observe(this, aBoolean -> {
            if (aBoolean) {
                dialog = Helper.progressDialog(LoginActivity.this);
            } else {
                dialog.dismiss();
            }
        });

        vm.getEmail().observe(this, s -> {
            if (Helper.isEmpty(s)) {
                inValid(0);
            } else if (!Helper.isValidEmail(s)) {
                b.etEmail.setError(getResources().getString(R.string.valid_email));
                inValid(0);
            } else {
                valid(0);
            }

        });

        vm.getPassword().observe(this, s -> {
            if (Helper.isEmpty(s)) {
                inValid(1);
            } else if (s.length() < 8) {
                b.loginPassword.setError(getResources().getString(R.string.valid_password));
                inValid(1);
            } else {
                valid(1);
            }
        });

        vm.getEnableLogin().observe(this, aBoolean -> {
            if (aBoolean) {
                if (vm.getIsEmailValid().getValue() && vm.getIsPasswordValid().getValue()) {
                    b.btnLogin.setEnabled(true);
                    b.btnLogin.setBackground(getResources().getDrawable(R.drawable.button_round_white));
                } else {
                    b.btnLogin.setEnabled(false);
                    b.btnLogin.setBackground(getResources().getDrawable(R.drawable.button_round_diable));
                }
            } else {
                b.btnLogin.setEnabled(false);
                b.btnLogin.setBackground(getResources().getDrawable(R.drawable.button_round_diable));
            }
        });






        inValid(0);
        inValid(1);

    }

    void inValid(int type) {
        if (type == 0) {
            vm.getIsEmailValid().setValue(false);
        } else if (type == 1) {
            vm.getIsPasswordValid().setValue(false);
        }
        vm.getEnableLogin().setValue(false);
    }

    void valid(int type) {
        if (type == 0) {
            vm.getIsEmailValid().setValue(true);
            b.etEmail.setError(null);
        } else if (type == 1) {
            vm.getIsPasswordValid().setValue(true);
            b.loginPassword.setError(null);
        }
        vm.getEnableLogin().setValue(true);

    }


    private void dialogForgotPassword() {
        d = new Dialog(LoginActivity.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_forgot_password);
        d.setCancelable(true);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        d.show();

        final EditText et_email = d.findViewById(R.id.et_email);
        Button bt_submit = d.findViewById(R.id.bt_submit);
        ImageView iv_back= d.findViewById(R.id.iv_back);

        bt_submit.setOnClickListener(view ->  {
                if (Helper.isEmpty(et_email.getText().toString())) {
                    et_email.setError(getResources().getString(R.string.empty_email));
                } else if (!Helper.isValidEmail(et_email.getText().toString())) {
                    et_email.setError(getResources().getString(R.string.valid_email));
                } else {
                    vm.forgotPasswordAPI(et_email.getText().toString());
                }

        });

        iv_back.setOnClickListener(view -> {
            d.dismiss();
        });

    }


    private void dialogResetPassword(String email) {
        d = new Dialog(LoginActivity.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_reset_password);
        d.setCancelable(true);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        d.show();

        final EditText et_code = d.findViewById(R.id.et_code);
        final EditText et_password = d.findViewById(R.id.et_password);
        Button bt_submit = d.findViewById(R.id.bt_reset);
        ImageView iv_back= d.findViewById(R.id.iv_back);

        bt_submit.setOnClickListener(v -> {
            vm.resetPasswordAPI(email,et_code.getText().toString(),et_password.getText().toString());

        });

        iv_back.setOnClickListener(view -> {
            d.dismiss();
        });


    }
}
