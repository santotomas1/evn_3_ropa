package com.example.evn_3_ropa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.evn_3_ropa.adapter.tiadapter;
import com.example.evn_3_ropa.modelo.productoT;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    Button btn_add,btn_fragment;
    RecyclerView mRecycler;
    tiadapter mAdapter;
    FirebaseFirestore mFirestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recycleViewSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("producto");

        FirestoreRecyclerOptions<productoT> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<productoT>().setQuery(query, productoT.class).build();

        mAdapter = new tiadapter(firestoreRecyclerOptions,this);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        btn_add = findViewById(R.id.btn_add);
        btn_fragment = findViewById(R.id.btn_fragment);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateProductoActivity.class));
            }
        });
        btn_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateProductoFragment fm = new CreateProductoFragment();
                fm.show(getSupportFragmentManager(),"Navegar a fragment");

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}