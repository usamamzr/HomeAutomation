package com.example.usama.homeautomation.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.usama.homeautomation.Adapters.RoomsRecyclerAdapter;
import com.example.usama.homeautomation.Models.Room;
import com.example.usama.homeautomation.R;

import java.util.ArrayList;

public class RoomsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        final ArrayList<Room> arrayList = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            arrayList.add(new Room("Name: " + i));
        }

        mRecyclerView = findViewById(R.id.rooms_recyclerView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RoomsRecyclerAdapter(arrayList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
