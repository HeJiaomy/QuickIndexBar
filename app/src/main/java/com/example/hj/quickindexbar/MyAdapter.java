package com.example.hj.quickindexbar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Friend> friendList;
    private Context context;

    MyAdapter(List<Friend> friendList, Context context) {
        this.friendList = friendList;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.letter.setText(friendList.get(i).getPinyin());
        holder.name.setText(friendList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView letter;
        private final TextView name;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            letter = itemView.findViewById(R.id.item_friend_letter_tv);
            name = itemView.findViewById(R.id.item_friend_name_tv);
        }
    }
}
