package com.xzit.app.activity;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityForgotPasswordBinding;
import com.xzit.app.listener.OnDialogClickListener;
import com.xzit.app.repository.ForgotPasswordRepository;
import com.xzit.app.retrofit.model.response.changepassword.ForgotPasswordResponse;
import com.xzit.app.utils.AppUtilsKt;
import com.xzit.app.utils.DialogUtilsKt;

import java.util.HashMap;

import static com.xzit.app.utils.AppUtilsKt.RESP_API_SUCCESS;
import static com.xzit.app.utils.AppUtilsKt.isEmailValid;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {


    private ActivityForgotPasswordBinding binding;
    ForgotPasswordRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();
        setObserver();

    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        repository = new ForgotPasswordRepository();
    }

    private void listener() {
        binding.imgbackscreen.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
    }

    private void setObserver() {
        repository.getResponseData().observe(this, new Observer<ForgotPasswordResponse>() {
            @Override
            public void onChanged(ForgotPasswordResponse response) {
                if (response != null && response.getStatus() == RESP_API_SUCCESS) {
                    DialogUtilsKt.showMessageDialog(mContext, response.getMessage(), false, new OnDialogClickListener() {
                        @Override
                        public void onButtonClicked(Boolean value) {
                            finish();
                        }
                    });

                } else {
                    DialogUtilsKt.showMessageDialog(mContext, response.getMessage(), true, new OnDialogClickListener() {
                        @Override
                        public void onButtonClicked(Boolean value) {

                        }
                    });
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgbackscreen:
                finish();
                break;
            case R.id.btnSubmit:
                callSignup();
                break;
        }
    }

    void callSignup() {

        String strEmail = binding.etEmail.getText().toString().trim();
        if (strEmail.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_email));
        }else if (!isEmailValid(strEmail)) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_valid_email));
        } else{
            HashMap<String, String> map = new HashMap<>();
            map.put("postData[requestCase]", "forgetPassword");
            map.put("postData[userName]", strEmail);
            repository.callForgotPassword(mContext, map);
        }

    }
}
