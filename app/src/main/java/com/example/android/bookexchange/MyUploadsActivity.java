package com.example.android.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyUploadsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    public static List<Books> mBooksList;
    private myBookAdapter mBookAdapter;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_uploads);

        mRecyclerView = findViewById(R.id.my_book_recycler_view);
        //mProgressBar = findViewById(R.id.progress_bar_main);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mBooksList = new ArrayList<>();
        mBookAdapter = new myBookAdapter(MyUploadsActivity.this,mBooksList);
        mBookAdapter.setListener(new myBookAdapter.Listener() {
            @Override
            public void onClick(int position) {

            }
        });


        mRecyclerView.setAdapter(mBookAdapter);
        //Might break
        /*Collections.reverse(mBooksList);*/
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("images");
        String id = mDatabaseReference.getKey();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Might break
                mBooksList.clear();
                //Hello Delete this later
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    //Collections.reverse(mBooksList);
                    Books books = snapshot.getValue(Books.class);
                    mBooksList.add(books);
                }
                //mProgressBar.setVisibility(View.INVISIBLE);
                Collections.reverse(mBooksList);
                mBookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(MyUploadsActivity.this,databaseError.getMessage(), Toast.LENGTH_SHORT,true).show();
                //mProgressBar.setVisibility(View.INVISIBLE);
            }
        });





    }


    }

