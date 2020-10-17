package com.xzit.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.adapter.StoryFilterAdapter;
import com.xzit.app.databinding.ActivityPublishStoryBinding;
import com.xzit.app.repository.StoryRepository;
import com.xzit.app.retrofit.model.response.login.LoginData;
import com.xzit.app.utils.AppUtilsKt;
import com.xzit.app.view.sticker.Sticker;
import com.xzit.app.view.sticker.TextSticker;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.xzit.app.utils.AppUtilsKt.getFilePathFromContentUri;

public class PublishStoryActivity extends BaseActivity implements View.OnClickListener {

    private Uri imageUri;
    private StoryRepository repository;
    private LoginData userData;

    public static Intent getIntent(Context context, Uri url) {
        Intent intent = new Intent(context, PublishStoryActivity.class);
        intent.putExtra(AppUtilsKt.PARAM_URL, url);
        return intent;
    }

    private ActivityPublishStoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();
        listener();
        initFilters();
    }

    private void initialization() {

        repository = new StoryRepository();
        userData = XzitApp.getLoginUserData();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_publish_story);
        imageUri = getIntent().getParcelableExtra(AppUtilsKt.PARAM_URL);
        binding.ivStoryImage.setImageURI(imageUri);
    }

    private void listener() {

        binding.ivBack.setOnClickListener(this);
        binding.ivAddText.setOnClickListener(this);
        binding.ivPencil.setOnClickListener(this);
        binding.btnPublish.setOnClickListener(this);
    }

    private void initFilters() {
        ArrayList<Integer> listColors = new ArrayList<Integer>();
        listColors.add(R.color.colorPrimary);
        listColors.add(R.color.colorGreen);
        listColors.add(R.color.colorAccentlight);
        listColors.add(R.color.colorPrimary);
        listColors.add(R.color.colorYellowLight);

        binding.viewPagerFilter.setAdapter(new StoryFilterAdapter(this, listColors));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivBack:
                finish();
                break;
            case R.id.ivCamera:
                Intent intent = new Intent(this, ResetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.ivGallery:

                break;


            case R.id.btnPublish:

                HashMap<String, RequestBody> mapReq = new HashMap<>();
                mapReq.put("postData[requestCase]", getRequestBody("createUserStory"));
                mapReq.put("postData[clientId]", getRequestBody(userData.getClientId()));
                mapReq.put("postData[userId]", getRequestBody(userData.getUserId()));
                mapReq.put("postData[location]", getRequestBody(binding.etLocation.getText().toString().trim()));
                mapReq.put("postData[caption]", getRequestBody(binding.etCaption.getText().toString().trim()));
                try {
                    repository.callAddStory(mContext, mapReq, getParamsRequestBody());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.ivAddText:
                TextSticker sticker = new TextSticker(getApplicationContext())
                        // .setDrawable(bubble)
                        .setText("Sticker\n")
                        .setMaxTextSize(34)
                        .setTextColor(Color.YELLOW)
                        .resizeText();
                sticker.setText("Hello");
                binding.stickerView.addSticker(sticker, Sticker.Position.CENTER
                );

                break;
        }
    }

    private ArrayList<MultipartBody.Part> getParamsRequestBody() throws URISyntaxException {

        ArrayList<MultipartBody.Part> attachmentName = new ArrayList<MultipartBody.Part>();
        File file = new File(getFilePathFromContentUri(this, imageUri));
        okhttp3.RequestBody requestFile =
                okhttp3.RequestBody.create(okhttp3.MediaType.parse("image/png"), file);
        /* val surveyBody = RequestBody.create(
                "image/png".toMediaTypeOrNull(),
                file)*/
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("attachmentName[" + 0 + "]", file.getName(), requestFile);
        attachmentName.add(body);
        return attachmentName;
    }

    okhttp3.RequestBody getRequestBody(String str) {
        okhttp3.RequestBody reqBody =
                okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), str);
        return reqBody;
    }
}
