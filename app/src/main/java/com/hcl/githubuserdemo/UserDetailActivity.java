package com.hcl.githubuserdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.hcl.githubuserdemo.databinding.ActivityUserDetailBinding;
import com.hcl.githubuserdemo.services.GitHubService;
import com.hcl.githubuserdemo.services.ServiceGenerator;
import com.hcl.githubuserdemo.services.models.GitHubUserDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity {

    private ActivityUserDetailBinding activityUserDetailBinding;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityUserDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_user_detail);

        getUser(getIntent().getStringExtra("login"));
        activityUserDetailBinding.imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activityUserDetailBinding.textViewBlogUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(url)){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }

            }
        });

    }

    private void getUser(String login){

        ServiceGenerator.createService(GitHubService.class).getUser(login).enqueue(new Callback<GitHubUserDetail>() {
            @Override
            public void onResponse(Call<GitHubUserDetail> call, Response<GitHubUserDetail> response) {
                if (response.isSuccessful()) {
                    activityUserDetailBinding.setUser(response.body());
                    url = response.body().getBlog();
                }
            }

            @Override
            public void onFailure(Call<GitHubUserDetail> call, Throwable t) {

            }
        });

    }

}
