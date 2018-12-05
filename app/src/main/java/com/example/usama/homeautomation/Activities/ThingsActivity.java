package com.example.usama.homeautomation.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.usama.homeautomation.Adapters.ThingsRecyclerAdapter;
import com.example.usama.homeautomation.Models.Thing;
import com.example.usama.homeautomation.R;

import java.util.ArrayList;

public class ThingsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_things);

        final ArrayList<Thing> arrayList = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            arrayList.add(new Thing("Name: " + i));
        }

        mRecyclerView = findViewById(R.id.things_recyclerView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ThingsRecyclerAdapter(arrayList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
