package com.hcl.githubuserdemo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hcl.githubuserdemo.presenter.DetailPresenter;
import com.hcl.githubuserdemo.presenter.DetailView;
import com.hcl.githubuserdemo.R;
import com.hcl.githubuserdemo.services.models.GitHubUserDetail;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailActivityMVP extends AppCompatActivity implements DetailView {

    private CircleImageView circleImageViewHeadPicture;
    private TextView textViewName, textViewBio, textViewLogin, textViewBadge, textViewLocation, textViewBlogUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_mvp);

        Log.e(" *** ","** UserDetailActivityMVP **");

        initView();
        new DetailPresenter(this, getIntent().getStringExtra("login"));
    }

    private void initView(){
        circleImageViewHeadPicture = findViewById(R.id.circleImageViewHeadPicture);
        textViewName = findViewById(R.id.textViewName);
        textViewBio = findViewById(R.id.textViewBio);
        textViewLogin = findViewById(R.id.textViewLogin);
        textViewBadge = findViewById(R.id.textViewBadge);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewBlogUrl = findViewById(R.id.textViewBlogUrl);
        findViewById(R.id.imageViewClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textViewBlogUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(textViewBlogUrl.getText().toString())) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(textViewBlogUrl.getText().toString()));
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void setData(GitHubUserDetail gitHubUserDetail) {
        Log.e(" *** ","** MVP setData **");
        Glide.with(this)
                .load(gitHubUserDetail.getAvatarUrl())
                .into(circleImageViewHeadPicture);

        textViewName.setText(gitHubUserDetail.getName());
        textViewBio.setText(gitHubUserDetail.getBio());
        textViewLogin.setText(gitHubUserDetail.getLogin());
        textViewBadge.setVisibility(gitHubUserDetail.getSiteAdmin() ? View.VISIBLE : View.GONE);
        textViewLocation.setText(gitHubUserDetail.getLocation());
        textViewBlogUrl.setText(gitHubUserDetail.getBlog());

    }
}
