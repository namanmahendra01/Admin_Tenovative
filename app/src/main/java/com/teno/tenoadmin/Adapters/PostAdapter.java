package com.teno.tenoadmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teno.tenoadmin.Models.post;
import com.teno.tenoadmin.R;
import com.teno.tenoadmin.ViewPost;


import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context mContext;
    private List<post> postList;

    public PostAdapter(Context mContext, List<post> postList) {
        this.mContext = mContext;
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.postf_for_verification, parent, false);
        return new com.teno.tenoadmin.Adapters.PostAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        post post=postList.get(i);

        holder.postNum.setText(String.valueOf(i+1));
        holder.postId.setText(post.getPostId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, ViewPost.class);
                i.putExtra("postId",post.getPostId());
                mContext.startActivity(i);
            }
        });

    }





    public long getItemId(int position) {
        post post = postList.get(position);
        return post.getImgUrl().hashCode();
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView postNum, postId;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            postNum = itemView.findViewById(R.id.postNum);
            postId = itemView.findViewById(R.id.postId);




        }
    }




}
