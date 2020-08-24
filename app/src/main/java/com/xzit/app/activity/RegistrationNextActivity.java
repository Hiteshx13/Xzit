package com.xzit.app.activity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.xzit.app.R;
import com.xzit.app.adapter.SpinnerCategoryAdapter;
import com.xzit.app.databinding.ActivitySignUpNextBinding;
import com.xzit.app.listener.OnDialogClickListener;
import com.xzit.app.repository.RegistrationRepository;
import com.xzit.app.retrofit.model.request.SignUpRequest;
import com.xzit.app.retrofit.model.response.masterdata.CATAGORYLIST;
import com.xzit.app.retrofit.model.response.registration.RegistrationResponse;
import com.xzit.app.utils.AppUtilsKt;
import com.xzit.app.utils.DialogUtilsKt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.xzit.app.activity.XzitApp.preference;
import static com.xzit.app.utils.AppUtilsKt.PARAM_SIGNUP_DATA;
import static com.xzit.app.utils.AppUtilsKt.REQ_SELECT_PHOTO_GALLERY;
import static com.xzit.app.utils.AppUtilsKt.RESP_API_SUCCESS;

public class RegistrationNextActivity extends BaseActivity implements View.OnClickListener {

    public static Intent getIntent(Context context, SignUpRequest signUpRequest) {
        Intent intent = new Intent(context, RegistrationNextActivity.class);
        intent.putExtra(PARAM_SIGNUP_DATA, signUpRequest);
        return intent;
    }

    private ActivitySignUpNextBinding binding;
    private List<CATAGORYLIST> listCategory;
    ArrayList<String> arrayCategory;
    private String strCategory;
    Uri profileUri;
    private SignUpRequest signUpRequest;
    private RegistrationRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();
        listener();
        setObserver();

        listCategory = preference.getMasterData(mContext).getResponse().getCATAGORY_LIST();
        listCategory.add(0, new CATAGORYLIST("", getString(R.string.select_a_category), ""));


        for (int i = 0; i < listCategory.size(); i++) {
            arrayCategory.add(listCategory.get(i).getVALUE());
        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                R.layout.row_spinner_category, R.id.tvCategoryName, strCategory);

        binding.spCategory.setAdapter(new SpinnerCategoryAdapter(mContext, listCategory));
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

    private void initialization() {
        arrayCategory = new ArrayList<>();
        repository = new RegistrationRepository();
        signUpRequest = (SignUpRequest) getIntent().getSerializableExtra(PARAM_SIGNUP_DATA);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_next);
    }

    private void listener() {
        binding.btnSignUp.setOnClickListener(this);
        binding.imgFacebook.setOnClickListener(this);
        binding.imgbackscreen.setOnClickListener(this);
        binding.imgGoogle.setOnClickListener(this);
        binding.ivProfile.setOnClickListener(this);

        binding.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    strCategory = "";
                } else {
                    strCategory = listCategory.get(position).getVALUE();
                }
                binding.spCategory.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    protected void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_SELECT_PHOTO_GALLERY);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                callSignUp();
//                Intent intent = new Intent(this, PreferenceMusicActivity.class);
//                startActivity(intent);
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

            case R.id.ivProfile:
                selectImageFromGallery();
                break;
        }
    }

    void callSignUp() {

        String strUserName = binding.etUserName.getText().toString().trim();
        String strTitle = binding.etTitle.getText().toString().trim();
        String strDescription = binding.etDescription.getText().toString().trim();
        String strTelephone = binding.etTelephone.getText().toString().trim();
        String strWebsite = binding.etWebsite.getText().toString().trim();
        String strBusinessHours = binding.etBusinessHours.getText().toString().trim();

        signUpRequest.setUsername(strUserName);
        signUpRequest.setTitle(strTitle);
        signUpRequest.setDescription(strDescription);
        signUpRequest.setTelephone(strTelephone);
        signUpRequest.setWebsite(strWebsite);
        signUpRequest.setBusinessHours(strBusinessHours);

        if (strUserName.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_user_name));
        } else if (strTitle.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_title));
        } else if (strDescription.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_description));
        } else if (strCategory.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_select_category));
        } else if (strTelephone.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_telephone));
        } else if (strBusinessHours.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_business_hours));
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("postData[requestCase]", "signup");
            map.put("postData[userType]", "BUSINESS");
            map.put("postData[businessName]", signUpRequest.getBusinessName());
            map.put("postData[email]", signUpRequest.getEmail());
            map.put("postData[password]", signUpRequest.getPassword());
            map.put("postData[confirmPass]", signUpRequest.getConfPassword());
            map.put("postData[userName]", strUserName);
            map.put("postData[title]", strTelephone);
            map.put("postData[category]", strCategory);
            map.put("postData[phone]", strTelephone);
            map.put("postData[website]", strWebsite);
            map.put("postData[businessHours]", strBusinessHours);

            if (profileUri != null) {
                File file = new File(profileUri.getPath());
                RequestBody fbody = RequestBody.create(MediaType.parse("image/*"),
                        file);
//                repository.callSignUp(mContext, map, fbody);
                repository.callSignUp(mContext, map);
            } else {
                repository.callSignUp(mContext, map);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_SELECT_PHOTO_GALLERY:
                if (resultCode == RESULT_OK) {
                    if (data.getData() != null) {

                        profileUri = data.getData();
                        binding.ivProfile.setImageURI(profileUri);
                    } else {
                        if (data.getClipData() != null) {
                            ClipData mClipData = data.getClipData();
                            for (int i = 0; i < mClipData.getItemCount(); i++) {
                                ClipData.Item item = mClipData.getItemAt(i);
                                profileUri = item.getUri();
                                binding.ivProfile.setImageURI(profileUri);
                            }
                            break;
                        }

                    }
                }
        }
    }
}
