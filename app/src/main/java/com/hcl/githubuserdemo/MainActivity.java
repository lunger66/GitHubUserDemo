package com.hcl.githubuserdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private GitHubService service;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private Headers headers;

    private Callback<List<GitHubUser>> callback =
            new Callback<List<GitHubUser>>() {
                @Override
                public void onResponse(Call<List<GitHubUser>> call,
                                       Response<List<GitHubUser>> response) {
                    if (response.isSuccessful()) {
                        List<GitHubUser> body = response.body();

//                        for (GitHubUser repo : body) {
////                            values.add(new PaginationItem(repo.getId(), repo.getName()));
//                        }

                        recyclerViewAdapter.addItem(body);
                        headers = response.headers();
//                        adapter.notifyDataSetChanged();
//                        fetchReposNextPage(headers);
                    } else {
                        Log.e("Request failed: ", "Cannot request GitHub repositories");
                    }
                }

                @Override
                public void onFailure(Call<List<GitHubUser>> call, Throwable t) {
                    Log.e("Error fetching repos", t.getMessage());
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);

        service = ServiceGenerator.createService(GitHubService.class);

        Call<List<GitHubUser>> call = service.userList(20);
        call.enqueue(callback);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1) && recyclerViewAdapter.getItemCount() < 100) {
                    fetchReposNextPage(headers);

                }
            }
        });
    }

    private void fetchReposNextPage(Headers headers) {
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
}
