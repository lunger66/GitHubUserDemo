package com.hcl.githubuserdemo.viewmode;

import android.text.TextUtils;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.hcl.githubuserdemo.adapters.RecyclerViewAdapter;
import com.hcl.githubuserdemo.services.GitHubService;
import com.hcl.githubuserdemo.services.ServiceGenerator;
import com.hcl.githubuserdemo.services.models.GitHubUser;
import com.hcl.githubuserdemo.utils.GitHubPagelinksUtils;

import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewMode {

    private RecyclerViewAdapter recyclerViewAdapter;
    private GitHubService service;
    private Headers headers;

    public MainViewMode(RecyclerViewAdapter.ItemClickListener itemClickListener) {

        recyclerViewAdapter = new RecyclerViewAdapter(itemClickListener);

        service = ServiceGenerator.createService(GitHubService.class);

        Call<List<GitHubUser>> call = service.userList(20);
        call.enqueue(callback);
    }

    private Callback<List<GitHubUser>> callback =
            new Callback<List<GitHubUser>>() {
                @Override
                public void onResponse(Call<List<GitHubUser>> call,
                                       Response<List<GitHubUser>> response) {
                    if (response.isSuccessful()) {
                        List<GitHubUser> body = response.body();

                        recyclerViewAdapter.addItem(body);
                        headers = response.headers();

                    } else {
                        Log.e("Request failed: ", "Cannot request GitHub repositories");
                    }
                }

                @Override
                public void onFailure(Call<List<GitHubUser>> call, Throwable t) {
                    Log.e("Error fetching repos", t.getMessage());
                }
            };

    public void fetchReposNextPage() {

        GitHubPagelinksUtils pagelinksUtils =
                new GitHubPagelinksUtils(headers);
        String next = pagelinksUtils.getNext();

//        Log.e("Header *** ", headers.get("Link"));

        if (TextUtils.isEmpty(next)) {
            return; // nothing to do
        }

        Call<List<GitHubUser>> call = service.userListPaginate(next);
        call.enqueue(callback);
    }

    public RecyclerViewAdapter getRecyclerViewAdapter() {
        return recyclerViewAdapter;
    }

}
