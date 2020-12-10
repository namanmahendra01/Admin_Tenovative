package com.teno.tenoadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teno.tenoadmin.Adapters.PostAdapter;
import com.teno.tenoadmin.Models.post;
import com.teno.tenoadmin.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "s";
    private RecyclerView postRv;
    private PostAdapter postAdapter;
    private ArrayList<post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postRv = findViewById(R.id.recyclerPost);

//set up recyclerView
        postRv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        postRv.setLayoutManager(linearLayoutManager);

        postList = new ArrayList<>();

        getPost();



    }

    private void getPost() {

        postList.clear();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(getString(R.string.dbname_Verification))
                .child(getString(R.string.dbname_post))
                .child(getString(R.string.field_user));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int x = 0;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        x++;
                        postList.add(snapshot1.getValue(post.class));
                        if (x == snapshot.getChildrenCount()) {
                            postAdapter = new PostAdapter(MainActivity.this, postList);
                            postAdapter.setHasStableIds(true);
                            postRv.setAdapter(postAdapter);
                        }

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (postAdapter!=null) {
            postAdapter.notifyDataSetChanged();
        }
        getPost();
        Log.d(TAG, "onRestart: ");
    }
}