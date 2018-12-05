package com.example.usama.homeautomation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.usama.homeautomation.Adapters.FloorsRecyclerAdapter;
import com.example.usama.homeautomation.R;

public class FloorsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String[] myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floors);
        mRecyclerView = (RecyclerView) findViewById(R.id.floors_recyclerView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FloorsRecyclerAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }
}
