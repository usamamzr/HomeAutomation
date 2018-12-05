package com.example.usama.homeautomation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.usama.homeautomation.R;
import com.example.usama.homeautomation.Adapters.RoomsRecyclerAdapter;

public class RoomsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String[] myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floors);
        mRecyclerView = (RecyclerView) findViewById(R.id.rooms_recyclerView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RoomsRecyclerAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }
}
