package com.xzit.app.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.adapter.StoryFilterAdapter;
import com.xzit.app.databinding.ActivityAddStoryBinding;
import com.xzit.app.listener.OnDialogClickListener;
import com.xzit.app.utils.CameraPreview;
import com.xzit.app.utils.DialogUtilsKt;
import com.xzit.app.utils.PermissionUtils;
import com.xzit.app.utils.RealPathUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static com.xzit.app.utils.AppUtilsKt.LaunchActivity;
import static com.xzit.app.utils.AppUtilsKt.REQ_SELECT_PHOTO_GALLERY;
import static com.xzit.app.utils.AppUtilsKt.REQ_WRITE_EXST;

public class AddStoryActivity extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback {


    private ActivityAddStoryBinding binding;
    private Uri imageUri;
    private boolean isBackLence = true;
    private Camera mCamera;
    private CameraPreview mPreview;
    public static boolean previewing = false;
    Camera.PictureCallback mPictureCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();
        listener();
    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_story);

    }

    private void listener() {
        binding.imgbackscreen.setOnClickListener(this);
        binding.ivCamera.setOnClickListener(this);
        binding.ivGallery.setOnClickListener(this);
        binding.ivSwitchCamera.setOnClickListener(this);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        //FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        binding.cameraPreview.addView(mPreview);


        mPictureCallback = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                mCamera.startPreview();
                File pictureFile = RealPathUtil.getOutputMediaFile(mContext, MEDIA_TYPE_IMAGE);
                if (pictureFile == null) {
                    Log.d("TAG", "Error creating media file, check storage permissions");
                    return;
                }

                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, null);
//
//                ExifInterface ei = new ExifInterface(photoPath);
//                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//                        ExifInterface.ORIENTATION_UNDEFINED);
//
                Bitmap rotatedBitmap = null;

               rotatedBitmap=rotateImage(bitmap,mPreview.getRotation());
//                switch(orientation) {
//
//                    case ExifInterface.ORIENTATION_ROTATE_90:
//                        rotatedBitmap = rotateImage(bitmap, 90);
//                        break;
//
//                    case ExifInterface.ORIENTATION_ROTATE_180:
//                        rotatedBitmap = rotateImage(bitmap, 180);
//                        break;
//
//                    case ExifInterface.ORIENTATION_ROTATE_270:
//                        rotatedBitmap = rotateImage(bitmap, 270);
//                        break;
//
//                    case ExifInterface.ORIENTATION_NORMAL:
//                    default:
//                        rotatedBitmap = bitmap;
//                }
                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//                    fos.write(rotatedBitmap);
                    fos.close();
                    LaunchActivity(mContext,PublishStoryActivity.getIntent(mContext,Uri.fromFile(pictureFile)));
                } catch (FileNotFoundException e) {
                    Log.d("TAG", "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d("TAG", "Error accessing file: " + e.getMessage());
                }
            }
        };
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgbackscreen:
                finish();
                break;
            case R.id.ivCamera:
                mCamera.takePicture(null, null, mPictureCallback);


//                Intent intent = PublishStoryActivity.getIntent(this, imageUri);
//                startActivity(intent);
                break;
            case R.id.ivGallery:
                if (PermissionUtils.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQ_WRITE_EXST)
                ) {
                    selectImageFromGallery();
                }
                break;
            case R.id.ivSwitchCamera:
                // gpuCameraRecorder.lensFacing(LensFacing.FRONT);
                //gpuCameraRecorder.notifyAll();
                // gpuCameraRecorder.lensFacing(LensFacing.BACK);
                // gpuCameraRecorder.notifyAll();
                //gpuCameraRecorder.build();
                isBackLence = !isBackLence;
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
                        imageUri = data.getData();
                        LaunchActivity(mContext,PublishStoryActivity.getIntent(mContext,imageUri));
                    } else {
                        if (data.getClipData() != null) {
                            ClipData mClipData = data.getClipData();
                            for (int i = 0; i < mClipData.getItemCount(); i++) {
                                ClipData.Item item = mClipData.getItemAt(i);
                                imageUri = item.getUri();
                                LaunchActivity(mContext,PublishStoryActivity.getIntent(mContext,imageUri));
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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        previewing = false;
    }
}
