package com.hcl.githubuserdemo.viewmode;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.hcl.githubuserdemo.databinding.ActivityUserDetailBinding;
import com.hcl.githubuserdemo.services.GitHubService;
import com.hcl.githubuserdemo.services.ServiceGenerator;
import com.hcl.githubuserdemo.services.models.GitHubUserDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailViewMode {

    private ActivityUserDetailBinding activityUserDetailBinding;
    private String url = "";

    public UserDetailViewMode(ActivityUserDetailBinding activityUserDetailBinding, final Activity activity, String login) {
        this.activityUserDetailBinding = activityUserDetailBinding;

        activityUserDetailBinding.imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        activityUserDetailBinding.textViewBlogUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(url)){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    activity.startActivity(intent);
                }

            }
        });

        getUser(login);
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
