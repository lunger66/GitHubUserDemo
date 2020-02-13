package com.hcl.githubuserdemo.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.hcl.githubuserdemo.R;
import com.hcl.githubuserdemo.databinding.ActivityUserDetailBinding;
import com.hcl.githubuserdemo.viewmode.UserDetailViewMode;

public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUserDetailBinding activityUserDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail);

        new UserDetailViewMode(activityUserDetailBinding, this, getIntent().getStringExtra("login"));

    }

}
