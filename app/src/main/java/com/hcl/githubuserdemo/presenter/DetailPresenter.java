package com.hcl.githubuserdemo.presenter;

import com.hcl.githubuserdemo.services.GitHubService;
import com.hcl.githubuserdemo.services.ServiceGenerator;
import com.hcl.githubuserdemo.services.models.GitHubUserDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter {

    private DetailView detailView;

    public DetailPresenter(DetailView detailView, String login) {
        this.detailView = detailView;
        if(null !=login)
            getUser(login);
    }

    private void getUser(String login){

        ServiceGenerator.createService(GitHubService.class).getUser(login).enqueue(new Callback<GitHubUserDetail>() {
            @Override
            public void onResponse(Call<GitHubUserDetail> call, Response<GitHubUserDetail> response) {
                if (response.isSuccessful()) {
                    detailView.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<GitHubUserDetail> call, Throwable t) {

            }
        });

    }
}
