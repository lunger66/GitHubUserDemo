package com.hcl.githubuserdemo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

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

        final UserDetailViewMode userDetailViewMode = new UserDetailViewMode(getIntent().getStringExtra("login"));

        activityUserDetailBinding.setUser(userDetailViewMode);

        activityUserDetailBinding.imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activityUserDetailBinding.textViewBlogUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(userDetailViewMode.getUrl())) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userDetailViewMode.getUrl()));
                    startActivity(intent);
                }

            }
        });

    }

}
