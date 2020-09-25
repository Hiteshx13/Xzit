package com.xzit.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xzit.app.R;
import com.xzit.app.retrofit.model.response.masterdata.Subtype;

import java.util.List;

public class SpinnerCategoryAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    List<Subtype> catagorylists;

    public SpinnerCategoryAdapter(Context applicationContext, List<Subtype> catagorylists) {
        this.context = applicationContext;
        this.catagorylists = catagorylists;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return catagorylists.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.row_spinner_category, null);
        TextView names = view.findViewById(R.id.tvCategoryName);
        names.setText(catagorylists.get(i).getDisplayValue());
        names.setTextColor(Color.BLACK);

        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }
}