package com.hcl.githubuserdemo.services;


import com.hcl.githubuserdemo.services.models.GitHubUser;
import com.hcl.githubuserdemo.services.models.GitHubUserDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GitHubService {

    @GET("users")
    Call<List<GitHubUser>> userList(
            @Query("per_page") Integer per_page);

    @GET
    Call<List<GitHubUser>> userListPaginate(@Url String url);

    @GET("users/{username}")
    Call<GitHubUserDetail> getUser(
            @Path("username") String username);

}
