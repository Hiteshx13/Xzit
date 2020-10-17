package com.xzit.app.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.iid.FirebaseInstanceId;
import com.xzit.app.R;
import com.xzit.app.databinding.ActivityDashboardBinding;
import com.xzit.app.fragment.ChatFragment;
import com.xzit.app.fragment.DashboardFragment;
import com.xzit.app.fragment.DiscoverFragment;
import com.xzit.app.fragment.InvitationFragment;
import com.xzit.app.listener.OnDialogClickListener;
import com.xzit.app.repository.DashboardRepository;
import com.xzit.app.retrofit.model.response.login.LoginData;
import com.xzit.app.utils.AppUtilsKt;
import com.xzit.app.utils.DialogUtilsKt;
import com.xzit.app.utils.PermissionUtils;

import java.util.HashMap;

import static com.xzit.app.activity.XzitApp.getLoginUserData;
import static com.xzit.app.utils.AppUtilsKt.REQ_CAMEEA;
import static com.xzit.app.utils.AppUtilsKt.REQ_WRITE_EXST;

public class DashboardActivity extends BaseActivity implements View.OnClickListener {

    private ActivityDashboardBinding binding;
    private DashboardRepository repository;
    private int TAB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        initialization();
        listener();

        TAB = getIntent().getIntExtra(AppUtilsKt.DASHBOARD_TAB, 0);

        switch (TAB) {
            case 0: {
                binding.ivHome.performClick();
                break;
            }
            case 1: {
                binding.ivDiscover.performClick();
                break;
            }
            case 2: {
                binding.ivInvitation.performClick();
                break;
            }
            case 3: {
                binding.ivChat.performClick();
                break;
            }
        }
    }

    private void initialization() {
        repository = new DashboardRepository();

        LoginData userdata = getLoginUserData();
        HashMap<String, String> map = new HashMap<>();
        map.put("postData[requestCase]", "fcmdeviceTokenUpdate");
        map.put("postData[userId]", userdata.getUserId());
        map.put("postData[fcmToken]", FirebaseInstanceId.getInstance().getToken());
        map.put("postData[deviceType]", "ANDROID");

        repository.callApi(mContext, map);
    }

    public void addFragment(Fragment fragment, boolean isBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        if (isBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }

        fragmentTransaction.commit(); // save the changes
    }


    private void listener() {
        binding.ivHome.setOnClickListener(this);
        binding.ivDiscover.setOnClickListener(this);
        binding.ivChat.setOnClickListener(this);
        binding.ivInvitation.setOnClickListener(this);
        binding.cvCamera.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivHome:
                addFragment(DashboardFragment.Companion.newInstance(), false);
                setSelection(view);
                break;
            case R.id.ivDiscover:
                addFragment(DiscoverFragment.Companion.newInstance(), false);
                setSelection(view);
                break;

            case R.id.ivChat:
                addFragment(ChatFragment.Companion.newInstance(), false);
                setSelection(view);
                break;
            case R.id.ivInvitation:
                addFragment(InvitationFragment.Companion.newInstance(), false);
                setSelection(view);
                break;
            case R.id.cvCamera:

                int PERMISSION_ALL = 1;
                String[] PERMISSIONS = {
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA
                };

                if (!PermissionUtils.hasPermissions(this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    openStoryActivity();
                }


                //String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                if (PermissionUtils.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQ_WRITE_EXST)
//                        && PermissionUtils.askForPermission(this, Manifest.permission.CAMERA, REQ_CAMEEA)
//                ) {
//                    openStoryActivity();
//                }

                break;
        }
    }

    void openStoryActivity() {
        startActivity(new Intent(this, AddStoryActivity.class));
    }

    void setSelection(View view) {
        binding.ivHome.setSelected(false);
        binding.ivDiscover.setSelected(false);
        binding.ivInvitation.setSelected(false);
        binding.ivChat.setSelected(false);

        view.setSelected(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //switch (requestCode) {

            if(PermissionUtils.isAllGranted(this, permissions)){
                openStoryActivity();
            }
//            if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
////                if (requestCode == REQ_WRITE_EXST) {
//////                    selectImageFromGallery();
////                }else if(){
////
////                }
//            }
            else {
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
       // }
    }
}
