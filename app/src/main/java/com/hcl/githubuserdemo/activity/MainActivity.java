package com.hcl.githubuserdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.hcl.githubuserdemo.R;
import com.hcl.githubuserdemo.adapters.RecyclerViewAdapter;
import com.hcl.githubuserdemo.databinding.ActivityMainBinding;
import com.hcl.githubuserdemo.services.models.GitHubUser;
import com.hcl.githubuserdemo.viewmode.MainViewMode;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBindings();

    }

    private void setupBindings() {

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onClick(GitHubUser user, int position) {
                Intent intent = new Intent(MainActivity.this, (position % 2) == 1 ? UserDetailActivityMVP.class : UserDetailActivityMVVM.class);
                intent.putExtra("login", user.getLogin());
                startActivity(intent);
            }
        });

        final MainViewMode mainViewMode = new MainViewMode(recyclerViewAdapter);
        activityMainBinding.setModel(mainViewMode);

        activityMainBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1) && recyclerViewAdapter.getItemCount() < 100) {
                    mainViewMode.fetchReposNextPage();

                }
            }
        });

    }

}
