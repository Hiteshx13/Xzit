package com.xzit.app.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.xzit.app.R;
import com.xzit.app.adapter.SpinnerCategoryAdapter;
import com.xzit.app.databinding.ActivitySignUpNextBinding;
import com.xzit.app.listener.OnDialogClickListener;
import com.xzit.app.repository.RegistrationRepository;
import com.xzit.app.retrofit.model.request.SignUpRequest;
import com.xzit.app.retrofit.model.response.masterdata.Subtype;
import com.xzit.app.retrofit.model.response.registration.RegistrationResponse;
import com.xzit.app.utils.AppUtilsKt;
import com.xzit.app.utils.DialogUtilsKt;
import com.xzit.app.utils.PermissionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;

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
    private List<Subtype> listCategory;

    static final Integer REQ_WRITE_EXST = 501;
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

        listCategory = preference.getMasterData(mContext).getResponse().get(0).getSubtype();
        Subtype title = new Subtype();
        title.setDisplayValue(getString(R.string.select_a_category));
        listCategory.add(0, title);


        for (int i = 0; i < listCategory.size(); i++) {
            arrayCategory.add(listCategory.get(i).getDisplayValue());
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
        binding.textsignin.setOnClickListener(this);

        binding.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    strCategory = "";
                } else {
                    strCategory = listCategory.get(position).getDisplayValue();
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                callSignUp();


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
                Intent intentSignIn = new Intent(RegistrationNextActivity.this, LoginActivity.class);
                startActivity(intentSignIn);
            case R.id.ivProfile:
                if (PermissionUtils.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQ_WRITE_EXST)
                ) {
                    selectImageFromGallery();
                } else {
                    DialogUtilsKt.showMessageDialog(this, this.getString(R.string.please_grant_all_required_permissions_from_application_setting), false, new OnDialogClickListener() {
                        @Override
                        public void onButtonClicked(Boolean value) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
                    //Toast.makeText(this, getString(R.string.please_grant_all_required_permissions_from_application_setting), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
//                File file = new File(profileUri.getPath());
//                RequestBody fbody = RequestBody.create(MediaType.parse("image/*"),
//                        file);
                HashMap<String, okhttp3.RequestBody> mapReq = new HashMap<>();
                mapReq.put("postData[requestCase]", getRequestBody("signup"));
                mapReq.put("postData[userType]", getRequestBody("BUSINESS"));
                mapReq.put("postData[businessName]", getRequestBody(signUpRequest.getBusinessName()));
                mapReq.put("postData[email]", getRequestBody(signUpRequest.getEmail()));
                mapReq.put("postData[password]", getRequestBody(signUpRequest.getPassword()));
                mapReq.put("postData[confirmPass]", getRequestBody(signUpRequest.getConfPassword()));
                mapReq.put("postData[userName]", getRequestBody(strUserName));
                mapReq.put("postData[title]", getRequestBody(strTelephone));
                mapReq.put("postData[category]", getRequestBody(strCategory));
                mapReq.put("postData[phone]", getRequestBody(strTelephone));
                mapReq.put("postData[website]", getRequestBody(strWebsite));
                mapReq.put("postData[businessHours]", getRequestBody(strBusinessHours));


                Log.d("#REQUEST", "" + mapReq);
//                getMultiPartBody("/storage/emulated/0/image.jpg")
                repository.callSignUpImage(mContext, mapReq,
                        getParamsRequestBody()
                );
                // repository.callSignUp(mContext, map);
            } else {
                repository.callSignUp(mContext, map);
            }
        }
    }

    //    private HashMap<String, RequestBody> getParamsRequestBody(HashMap<String, String> param)  {
//        HashMap<String, RequestBody> resultParams = new HashMap<String, RequestBody>();
//
////        for (int i=0;i<param.size();i++) {
////            int body = param.get(i) { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
////            body?.let { resultParams.put(key, it) }
////        }
//
//        if (!TextUtils.isEmpty(userImages)) {
//            File file = new File(profileUri.getPath());
//            RequestBody fbody = RequestBody.create(MediaType.parse("image/*"),
//                    file);
//           // val reqFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file);
//            val imageParams = ApiParam.IMAGES + "\";filename=\"${file.name}\""
//            resultParams.put(imageParams,reqFile);
//        }
//        return resultParams
//    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getRealPathFromURI(Uri contentUri) {
        // Will return "image:x*"
        String wholeID = DocumentsContract.getDocumentId(contentUri);

// Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

// where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);

        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }

        cursor.close();
        return filePath;
    }

    okhttp3.RequestBody getRequestBody(String str) {
        okhttp3.RequestBody reqBody =
                okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), str);
        return reqBody;
    }

    MultipartBody.Part getMultiPartBody(String imagePath) {
        //pass it like this
        File file = new File(imagePath);
        okhttp3.RequestBody requestFile =
                okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("attachmentName[" + 0 + "]", "", requestFile);

        return body;
    }

    private MultipartBody.Part getParamsRequestBody() {

        File file = new File("/storage/emulated/0/image.jpg");
        okhttp3.RequestBody requestFile =
                okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("attachmentName[" + 0 + "]", "image.jpg", requestFile);

        return body;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == REQ_WRITE_EXST) {
                selectImageFromGallery();
            }
        } else {
            DialogUtilsKt.showMessageDialog(this, this.getString(R.string.please_grant_all_required_permissions_from_application_setting), false, new OnDialogClickListener() {
                @Override
                public void onButtonClicked(Boolean value) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            // Toast.makeText(this, "Grant all required permission from application settings", Toast.LENGTH_SHORT).show();
        }
    }
}
