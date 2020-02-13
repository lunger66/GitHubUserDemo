package com.hcl.githubuserdemo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hcl.githubuserdemo.databinding.MyCustomViewBinding;
import com.hcl.githubuserdemo.viewmode.UserDetailViewMode;

public class MyCustomView extends LinearLayout {

    private MyCustomViewBinding mBinding;

    public MyCustomView(Context context) {
        super(context);
        init(context);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
    }

    public void setMyViewModel(UserDetailViewMode obj) {
        mBinding.setMyViewModel(obj);
    }
}
