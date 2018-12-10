package com.example.usama.homeautomation.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.usama.homeautomation.API.LaravelAPI;
import com.example.usama.homeautomation.Adapters.RoomsRecyclerAdapter;
import com.example.usama.homeautomation.Models.Room;
import com.example.usama.homeautomation.R;
import com.example.usama.homeautomation.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RoomsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RoomsRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int FloorId;

    Retrofit retrofit = RetrofitClient.getRetrofit();
    final LaravelAPI service = retrofit.create(LaravelAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        if (getIntent() != null) {

            FloorId = getIntent().getIntExtra("floorId",0);
        }

        final ArrayList<Room> arrayList = new ArrayList<>();

//        for (int i = 1; i <= 5; i++) {
//            arrayList.add(new Room("Room: " + i));
//        }

        mRecyclerView = findViewById(R.id.rooms_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RoomsRecyclerAdapter(arrayList);
        mRecyclerView.setAdapter(mAdapter);


        Call<ArrayList<Room>> roomsList = service.getRoomByFloor(String.valueOf(FloorId));

        roomsList.enqueue(new Callback<ArrayList<Room>>() {
            @Override
            public void onResponse(Call<ArrayList<Room>> call, Response<ArrayList<Room>> response) {
                ArrayList<Room> rooms = response.body();
                mAdapter.setRoomList(rooms);
                Log.i("responseCheckRoom", "onResponse(): " + "call: " + call + " response: " + response);
            }

            @Override
            public void onFailure(Call<ArrayList<Room>> call, Throwable t) {
                Log.i("responseCheckRoom", "onFailure(): " + "call: " + call + " t: " + t);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navigation_add) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RoomsActivity.this);
            View view = LayoutInflater.from(RoomsActivity.this).inflate(R.layout.new_dialouge, null);
            builder.setView(view);
            final EditText editText = new EditText(RoomsActivity.this);
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setMessage("Enter Room Name").setTitle("Enter Room here");
            builder.setView(editText);
            builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Call<Room> SetRoom= service.addRooms(editText.getText().toString(),FloorId);
                    SetRoom.enqueue(new Callback<Room>() {
                        @Override
                        public void onResponse(Call<Room> call, Response<Room> response) {

                        }

                        @Override
                        public void onFailure(Call<Room> call, Throwable t) {

                        }
                    });
                }
            });
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener()

            {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
