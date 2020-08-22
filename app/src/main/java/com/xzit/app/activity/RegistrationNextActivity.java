package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.adapter.SpinnerCategoryAdapter;
import com.xzit.app.databinding.ActivitySignUpNextBinding;
import com.xzit.app.retrofit.model.response.login.masterdata.CATAGORYLIST;

import java.util.ArrayList;
import java.util.List;

public class RegistrationNextActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySignUpNextBinding binding;
    private List<CATAGORYLIST> listCategory;
    ArrayList<String> strCategory;
    private String category;
    //  String[] arrDay = {"Day", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();
        listener();


        listCategory = preference.getMasterData(mContext).getResponse().getCATAGORY_LIST();
        // ArrayAdapter<String> adapterDay = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrDay);

//        ArrayAdapter<CATAGORYLIST> adapter = new ArrayAdapter<CATAGORYLIST>(this,
//                R.layout.row_spinner_category, CATAGORY_LIST);
        listCategory.add(0, new CATAGORYLIST("", getString(R.string.select_a_category), ""));


        for (int i = 0; i < listCategory.size(); i++) {
            strCategory.add(listCategory.get(i).getVALUE());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.row_spinner_category, R.id.tvCategoryName, strCategory);

//        binding.spCategory.setAdapter(adapter);
        binding.spCategory.setAdapter(new SpinnerCategoryAdapter(mContext, listCategory));
//        binding.spCategory.setAdapter(adapter);
    }

    private void initialization() {
        strCategory = new ArrayList<>();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_next);
    }

    private void listener() {
        binding.btnSignUp.setOnClickListener(this);
        binding.imgFacebook.setOnClickListener(this);

        binding.imgbackscreen.setOnClickListener(this);
        binding.imgGoogle.setOnClickListener(this);


        binding.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    category = "";
                } else {
                    category = listCategory.get(position).getVALUE();
                }
                binding.spCategory.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                Intent intent = new Intent(this, PreferenceMusicActivity.class);
                startActivity(intent);
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
        }
    }
}
