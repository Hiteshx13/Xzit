package com.xzit.app.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityCreatEventBinding;
import com.xzit.app.listener.OnDialogClickListener;
import com.xzit.app.utils.DialogUtilsKt;
import com.xzit.app.utils.PermissionUtils;

import java.util.Calendar;
import java.util.Date;

import static com.xzit.app.utils.AppUtilsKt.REQ_SELECT_PHOTO_GALLERY;
import static com.xzit.app.utils.AppUtilsKt.REQ_WRITE_EXST;

public class CreatEventActivity extends BaseActivity implements View.OnClickListener {

    private ActivityCreatEventBinding binding;
    private Uri profileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();
        listener();
    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_creat_event);
        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
    }

    private void listener() {
        binding.btnNext.setOnClickListener(this);
        binding.llSelectImage.setOnClickListener(this);
        binding.etEventDuration.setOnClickListener(this);
        binding.etEventTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnNext:
                Intent intentSettings = new Intent(this, AddFriendsActivity.class);
                startActivity(intentSettings);
                break;

            case R.id.etEventDuration:
                DialogUtilsKt.showDatePickerDialog(mContext, true, new OnDialogClickListener() {
                    @Override
                    public void onButtonClicked(Boolean value) {

                    }
                });
                break;
            case R.id.etEventTime:
                DialogUtilsKt.showTimePickerDialog(mContext, true, new OnDialogClickListener() {
                    @Override
                    public void onButtonClicked(Boolean value) {

                    }
                });
//                SnapTimePickerDialog dialog = new SnapTimePickerDialog();
//                dialog.show(getSupportFragmentManager(),SnapTimePickerDialog.TAG);
//                        d
//                SnapTimePickerDialog.Builder().apply {
//                useViewModel()
//            }.build().show(mActivity.supportFragmentManager, SnapTimePickerDialog.TAG)

//            new SnapTimePickerUtil().observe(this,(int selectedMinute,int hour)) {
//                selectedHour:
//                int, int selectedMinute->
//                        Log.d("" + selectedHour, "" + selectedMinute);
//            }
            break;

            case R.id.llSelectImage:
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

    protected void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_SELECT_PHOTO_GALLERY);
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
                        binding.llSelectImageRoot.setVisibility(View.GONE);
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
