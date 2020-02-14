package com.hcl.githubuserdemo.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hcl.githubuserdemo.activity.MainActivity;
import com.hcl.githubuserdemo.R;
import com.hcl.githubuserdemo.activity.UserDetailActivityMVVM;
import com.hcl.githubuserdemo.activity.UserDetailActivityMVP;
import com.hcl.githubuserdemo.databinding.ListItemBinding;
import com.hcl.githubuserdemo.services.models.GitHubUser;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<GitHubUser> userList = new ArrayList<>();
    private MainActivity mainActivity;

    public RecyclerViewAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void addItem(List<GitHubUser> list) {
        userList.addAll(list);
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item, parent, false);

        return new ViewHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GitHubUser user = userList.get(position);
        ((ViewHolder)holder).bind(user, position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ListItemBinding listItemBinding;

        public ViewHolder(ListItemBinding listItemBinding){
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;

        }

        void bind(final GitHubUser user, final int position){
            listItemBinding.setUser(user);
            listItemBinding.executePendingBindings();
            listItemBinding.textViewNum.setText(String.valueOf(position + 1));
            listItemBinding.frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mainActivity, (position % 2) == 1 ? UserDetailActivityMVP.class : UserDetailActivityMVVM.class);
                    intent.putExtra("login",user.getLogin());
                    mainActivity.startActivity(intent);

                }
            });
        }

    }

}
