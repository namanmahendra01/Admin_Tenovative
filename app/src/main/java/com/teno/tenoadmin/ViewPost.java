package com.teno.tenoadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teno.tenoadmin.Models.post;

public class ViewPost extends AppCompatActivity {

    private static final String TAG = "viewpost";
    private String postId;
    private TextView allow, reject, caption;
    private ImageView post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);


        allow = findViewById(R.id.allow);
        reject = findViewById(R.id.reject);
        post = findViewById(R.id.post);
        caption = findViewById(R.id.caption);

        Intent i = getIntent();
        postId = i.getStringExtra("postId");

        getPost(postId);


        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowPost(postId);
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RejectPost(postId);

            }
        });

    }

    private void RejectPost(String postId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(getString(R.string.dbname_Verification))
                .child(getString(R.string.dbname_post))
                .child(getString(R.string.field_user))
                .child(postId);
                ref.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                    }
                });
    }

    private void AllowPost(String postId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(getString(R.string.dbname_Verification))
                .child(getString(R.string.dbname_post))
                .child(getString(R.string.field_user))
                .child(postId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference(getString(R.string.dbname_post))
                            .child(getString(R.string.field_user))
                            .child(postId);
                    ref1.setValue(snapshot.getValue(post.class))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    ref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            finish();
                                        }
                                    });
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getPost(String postId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(getString(R.string.dbname_Verification))
                .child(getString(R.string.dbname_post))
                .child(getString(R.string.field_user))
                .child(postId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Log.d(TAG, "onDataChange: " + snapshot.child("imgUrl").getValue().toString());
                    Glide.with(getApplicationContext())
                            .load(snapshot.child("imgUrl").getValue().toString())
                            .into(post);

                    caption.setText(snapshot.child("cap").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}