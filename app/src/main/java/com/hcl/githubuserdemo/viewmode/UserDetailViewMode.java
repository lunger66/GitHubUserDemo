package com.hcl.githubuserdemo.viewmode;


import androidx.databinding.ObservableField;

import com.hcl.githubuserdemo.services.GitHubService;
import com.hcl.githubuserdemo.services.ServiceGenerator;
import com.hcl.githubuserdemo.services.models.GitHubUserDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailViewMode {

    private String url = "";
    public ObservableField<GitHubUserDetail> gitHubUserDetail = new ObservableField<>();

    public UserDetailViewMode(String login) {

        getUser(login);
    }

    private void getUser(String login){

        ServiceGenerator.createService(GitHubService.class).getUser(login).enqueue(new Callback<GitHubUserDetail>() {
            @Override
            public void onResponse(Call<GitHubUserDetail> call, Response<GitHubUserDetail> response) {
                if (response.isSuccessful()) {
                    gitHubUserDetail.set(response.body());
                    url = response.body().getBlog();
                }
            }

            @Override
            public void onFailure(Call<GitHubUserDetail> call, Throwable t) {

            }
        });

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
