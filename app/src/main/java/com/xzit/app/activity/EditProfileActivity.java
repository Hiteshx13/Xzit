package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xzit.app.R;
import com.xzit.app.databinding.ActivityEditProfileBinding;
import com.xzit.app.listener.OnDialogClickListener;
import com.xzit.app.repository.EditProfileRepository;
import com.xzit.app.retrofit.model.response.editprofile.EditProfileResponse;
import com.xzit.app.retrofit.model.response.login.LoginData;
import com.xzit.app.retrofit.model.response.login.LoginResponse;
import com.xzit.app.utils.AppUtilsKt;
import com.xzit.app.utils.DialogUtilsKt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.xzit.app.activity.XzitApp.preference;
import static com.xzit.app.utils.AppUtilsKt.RESP_API_SUCCESS;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener {


    private ActivityEditProfileBinding binding;
    EditProfileRepository repository;
    String DOB = "";
    LoginData userdata;
    LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();
        listener();
        setObserver();
    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        repository = new EditProfileRepository();

        loginResponse = preference.getUserData(mContext);
        userdata = loginResponse.getResponse().get(0);
        DOB = userdata.getDOB();
        binding.tvName.setText(userdata.getUsername());
        binding.etFirstName.setText(userdata.getUsername());
        binding.etTitle.setText(userdata.getTitle());
        binding.etPhone.setText(userdata.getPhone());
        binding.etGender.setText(userdata.getGender());
        binding.etDOB.setText(userdata.getDOB());
        binding.etWebsite.setText(userdata.getWebsite());
    }

    private void listener() {

        binding.imgbackscreen.setOnClickListener(this);
        binding.tvResetPassword.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
        binding.etDOB.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgbackscreen:
                finish();
                break;
            case R.id.tvResetPassword:
                Intent intent = new Intent(this, ResetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.etDOB:
                openDatePicker();
                break;
            case R.id.btnSave:
                callSignup();
                break;
        }
    }

    void openDatePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        DOB = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;//ppUtilsKt.getStringDate(calendars.get(0).getTime());
                        binding.etDOB.setText(DOB);
                    }
                },
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection

        );
        //dpd.setAccentColor(mContext.getColor(R.color.colorAccent));
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    void callSignup() {

        String strPhone = binding.etPhone.getText().toString().trim();
        String strTitle = binding.etTitle.getText().toString().trim();
        String strGender = binding.etGender.getText().toString().trim();
        String strWebsite = binding.etWebsite.getText().toString().trim();

        userdata.setPhone(strPhone);
        userdata.setGender(strGender);
        userdata.setWebsite(strWebsite);
        userdata.setDOB(DOB);
        userdata.setTitle(strTitle);
        //userdata.set(strWebsite);

        if (strTitle.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_title));
        } else if (strPhone.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_phone));
        } else if (DOB.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_select_dob));
        } else if (strGender.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_gender));
        } else if (strWebsite.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_website));
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("postData[requestCase]", "editProfile");
            map.put("postData[businessName]", userdata.getUsername());
            map.put("postData[userId]", userdata.getUserId());
            map.put("postData[title]", strTitle);
            map.put("postData[DOB]", DOB);
            map.put("postData[description]", userdata.getDescription());
            map.put("postData[category]", userdata.getCategory());
            map.put("postData[phone]", strPhone);
            map.put("postData[website]", strWebsite);
            map.put("postData[businessHours]", userdata.getBusinessHours());
            repository.callEditProfile(mContext, map);
        }

    }

    private void setObserver() {
        repository.getResponseData().observe(this, new Observer<EditProfileResponse>() {
            @Override
            public void onChanged(EditProfileResponse response) {
                if (response != null && response.getStatus() == RESP_API_SUCCESS) {

                    List<LoginData> list = new ArrayList<>();
                    loginResponse = preference.getUserData(mContext);
                    list.add(userdata);
                    loginResponse.setResponse(list);
                    preference.saveLoginData(mContext, loginResponse);
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
}
