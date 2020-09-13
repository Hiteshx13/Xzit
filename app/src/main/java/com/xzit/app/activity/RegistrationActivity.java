package com.xzit.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xzit.app.R;
import com.xzit.app.databinding.ActivitySignUpBinding;
import com.xzit.app.listener.OnDialogClickListener;
import com.xzit.app.repository.RegistrationRepository;
import com.xzit.app.retrofit.model.request.SignUpRequest;
import com.xzit.app.retrofit.model.response.registration.RegistrationResponse;
import com.xzit.app.utils.AppUtilsKt;
import com.xzit.app.utils.DialogUtilsKt;

import java.util.Calendar;
import java.util.HashMap;

import static com.xzit.app.utils.AppUtilsKt.RESP_API_SUCCESS;
import static com.xzit.app.utils.AppUtilsKt.VALIDATION_password_length;
import static com.xzit.app.utils.AppUtilsKt.isEmailValid;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {

    public static Intent getIntent(Context context, String type) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        intent.putExtra(AppUtilsKt.PARAM_SIGNUP_TYPE, type);
        return intent;
    }

    private ActivitySignUpBinding binding;
    private String SIGNUP_TYPE;
    private String GENDER_SELECTED = "MALE";
    private String DOB = "";
    private RegistrationRepository repository;
    private SignUpRequest signUpRequest;
    private boolean isShowPassword = false;
    private boolean isShowConfPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();
        listener();
        setObserver();
        if (SIGNUP_TYPE.equals(AppUtilsKt.SIGNUP_TYPE_MERCHENT)) {
            binding.llGender.setVisibility(View.GONE);
            binding.etName.setHint(getString(R.string.businessname));
            binding.cvBirthDate.setVisibility(View.GONE);

        } else if (SIGNUP_TYPE.equals(AppUtilsKt.SIGNUP_TYPE_USER)) {
            binding.llGender.setVisibility(View.VISIBLE);
            binding.btnMale.callOnClick();
            binding.etName.setHint(getString(R.string.username));
            binding.btnNext.setText(getString(R.string.signup));
            binding.cvBirthDate.setVisibility(View.VISIBLE);
        }
    }

    private void initialization() {
        repository = new RegistrationRepository();
        SIGNUP_TYPE = getIntent().getStringExtra(AppUtilsKt.PARAM_SIGNUP_TYPE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
    }

    private void listener() {
        binding.btnNext.setOnClickListener(this);
        binding.textsignin.setOnClickListener(this);
        binding.btnFacebook.setOnClickListener(this);

        binding.imgbackscreen.setOnClickListener(this);
        binding.btnGoogle.setOnClickListener(this);
        binding.btnMale.setOnClickListener(this);
        binding.btnFemale.setOnClickListener(this);
        binding.etBirthDate.setOnClickListener(this);

        binding.etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (binding.etPassword.getRight())) {
                        isShowPassword = !isShowPassword;
                        if (isShowPassword) {
                            binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        } else {
                            binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        binding.etConfPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (binding.etConfPassword.getRight())) {
                        isShowConfPassword = !isShowConfPassword;
                        if (isShowConfPassword) {
                            binding.etConfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        } else {
                            binding.etConfPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtsignup:
                break;
            case R.id.btnNext:
                if (SIGNUP_TYPE.equals(AppUtilsKt.SIGNUP_TYPE_MERCHENT)) {
                    if (isValid()) {
                        Intent intentNext = RegistrationNextActivity.getIntent(mContext, signUpRequest);
                        startActivity(intentNext);
                    }
                } else if (SIGNUP_TYPE.equals(AppUtilsKt.SIGNUP_TYPE_USER)) {
                    callSignup();
                }

                break;
            case R.id.imgFacebook:
                Intent intentForgetpassword = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intentForgetpassword);
                break;
            case R.id.imgbackscreen:
                finish();
                break;
            case R.id.imgGoogle:
                Intent intentEditprofile = new Intent(this, EditProfileActivity.class);
                startActivity(intentEditprofile);
                break;
            case R.id.textsignin:
                Intent intentSignIn = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intentSignIn);
                break;
            case R.id.btnMale:
                binding.btnMale.setSelected(true);
                binding.btnFemale.setSelected(false);
                GENDER_SELECTED = "MALE";
                break;
            case R.id.btnFemale:
                binding.btnMale.setSelected(false);
                binding.btnFemale.setSelected(true);
                GENDER_SELECTED = "FEMALE";
                break;

            case R.id.btnGoogle:
                Intent signInIntent = getMGoogleSignInClient().getSignInIntent();
                startActivityForResult(signInIntent, AppUtilsKt.REQ_LOGIN_WITH_GMAIL);
                break;

            case R.id.btnFacebook:
                break;
            case R.id.etBirthDate:
                openDatePicker();
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
                        binding.etBirthDate.setText(DOB);
                    }
                },
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection

        );
        //dpd.setAccentColor(mContext.getColor(R.color.colorAccent));
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    boolean isValid() {

        boolean isValid = false;
        String strName = binding.etName.getText().toString().trim();
        String strEmail = binding.etEmail.getText().toString().trim();
        String strPassword = binding.etPassword.getText().toString().trim();
        String strConfPassword = binding.etConfPassword.getText().toString().trim();

        signUpRequest = new SignUpRequest();
        signUpRequest.setBusinessName(strName);
        signUpRequest.setEmail(strEmail);
        signUpRequest.setPassword(strPassword);
        signUpRequest.setConfPassword(strConfPassword);

        if (SIGNUP_TYPE.equals(AppUtilsKt.SIGNUP_TYPE_MERCHENT)) {
            if (strName.isEmpty()) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_business_name));
            } else if (strEmail.isEmpty()) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_email));
            } else if (!isEmailValid(strEmail)) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_valid_email));
            } else if (strPassword.isEmpty()) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_password));
            } else if (strConfPassword.isEmpty()) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_conf_password));
            } else if (strConfPassword.length() < VALIDATION_password_length) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_at_least_password));
            } else if (!strPassword.equals(strConfPassword)) {
                AppUtilsKt.showToast(mContext, getString(R.string.password_does_not_match));
            } else {
                isValid = true;
            }

        } else if (SIGNUP_TYPE.equals(AppUtilsKt.SIGNUP_TYPE_USER)) {
            if (strName.isEmpty()) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_user_name));
            } else if (strEmail.isEmpty()) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_email));
            } else if (!isEmailValid(strEmail)) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_valid_email));
            } else if (DOB.isEmpty()) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_select_dob));
            } else if (!AppUtilsKt.isEmailValid(strEmail)) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_valid_email));
            } else if (strPassword.isEmpty()) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_password));
            } else if (strConfPassword.isEmpty()) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_conf_password));
            } else if (strConfPassword.length() < VALIDATION_password_length) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_at_least_password));
            } else if (!strPassword.equals(strConfPassword)) {
                AppUtilsKt.showToast(mContext, getString(R.string.password_does_not_match));
            } else {
                isValid = true;
            }
        }
        return isValid;
    }

    private void setObserver() {
        repository.getResponseData().observe(this, new Observer<RegistrationResponse>() {
            @Override
            public void onChanged(RegistrationResponse response) {
                if (response != null && response.getStatus() == RESP_API_SUCCESS) {
                    DialogUtilsKt.showMessageDialog(mContext, response.getMessage(), false, new OnDialogClickListener() {
                        @Override
                        public void onButtonClicked(Boolean value) {
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            startActivity(intent);
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

    void callSignup() {

        String strName = binding.etName.getText().toString().trim();
        String strEmail = binding.etEmail.getText().toString().trim();
        String strPassword = binding.etPassword.getText().toString().trim();
        String strConfPassword = binding.etConfPassword.getText().toString().trim();

        if (isValid()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("postData[requestCase]", "signup");
            map.put("postData[userType]", "NORMAL");
            map.put("postData[userName]", strName);
            map.put("postData[email]", strEmail);
            map.put("postData[gender]", GENDER_SELECTED);
            map.put("postData[password]", strPassword);
            map.put("postData[confirmPass]", strConfPassword);
            repository.callSignUp(mContext, map);
            map.put("postData[password]", strPassword);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppUtilsKt.REQ_LOGIN_WITH_GMAIL) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    void updateUI(GoogleSignInAccount account) {
        Log.w("TAG", "signInResult:failed code=");
    }
}
