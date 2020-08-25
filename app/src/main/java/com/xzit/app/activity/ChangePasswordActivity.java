package com.xzit.app.activity;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityChangePasswordBinding;
import com.xzit.app.listener.OnDialogClickListener;
import com.xzit.app.repository.ChangePasswordRepository;
import com.xzit.app.retrofit.model.response.changepassword.ChangePasswordResponse;
import com.xzit.app.retrofit.model.response.login.LoginResponse;
import com.xzit.app.utils.AppPreference;
import com.xzit.app.utils.AppUtilsKt;
import com.xzit.app.utils.DialogUtilsKt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import static com.xzit.app.utils.AppUtilsKt.RESP_API_SUCCESS;
import static com.xzit.app.utils.AppUtilsKt.VALIDATION_password_length;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    private ActivityChangePasswordBinding binding;
    private ChangePasswordRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();
        listener();
        initObserver();
    }

    private void initialization() {
        repository = new ChangePasswordRepository();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
    }

    private void listener() {
        binding.imgbackscreen.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
    }

    private void initObserver() {
        repository.getResponseData().observe(this, new Observer<ChangePasswordResponse>() {
            @Override
            public void onChanged(ChangePasswordResponse response) {
                if (response.getStatus() == RESP_API_SUCCESS) {
                    DialogUtilsKt.showMessageDialog(mContext, response.getMessage(), false, new OnDialogClickListener() {
                        @Override
                        public void onButtonClicked(Boolean value) {
                            finish();
                        }
                    });
                } else {
                    DialogUtilsKt.showMessageDialog(mContext, response.getMessage(), false, new OnDialogClickListener() {
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
            case R.id.btnSave:

                callChangePassword();
                break;
        }
    }


    void callChangePassword() {
        AppPreference preference = new AppPreference();
        LoginResponse userdata = preference.getUserData(mContext);

        String strCurrentPass = binding.etCurrentPassword.getText().toString().trim();
        String strNewPass = binding.etNewPassword.getText().toString().trim();
        String strConfPass = binding.etConfirmPassword.getText().toString().trim();

        if (strCurrentPass.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_current_password));
        } else if (strNewPass.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_new_password));
        }else if (strNewPass.length() < VALIDATION_password_length) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_at_least_password));
        } else if (strConfPass.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_conf_password));
        } else if (!strNewPass.equals(strNewPass)) {
            AppUtilsKt.showToast(mContext, getString(R.string.password_does_not_match));
        }else {

            HashMap<String, String> map = new HashMap<>();
            map.put("postData[requestCase]", "changePassword");
            map.put("postData[userId]", userdata.getResponse().get(0).getUserId());
            map.put("postData[currentpassword]", strCurrentPass);
            map.put("postData[newPassword]", strNewPass);
            map.put("postData[confirmPassword]", strConfPass);
            repository.callChangePassword(mContext, map);
        }
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
