package com.example.usama.homeautomation.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.Toast;

import com.example.usama.homeautomation.API.LaravelAPI;
import com.example.usama.homeautomation.Adapters.FloorsRecyclerAdapter;
import com.example.usama.homeautomation.Models.Floor;
import com.example.usama.homeautomation.Models.User;
import com.example.usama.homeautomation.R;
import com.example.usama.homeautomation.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FloorsRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    ArrayList arrayList = new ArrayList();
    Retrofit retrofit = RetrofitClient.getRetrofit();
    final LaravelAPI service = retrofit.create(LaravelAPI.class);


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*final ArrayList<Floor> arrayList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            arrayList.add(new Floor("Floor: " + i));
        }*/

        mRecyclerView = findViewById(R.id.floors_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FloorsRecyclerAdapter(arrayList);
        mRecyclerView.setAdapter(mAdapter);


        Call<ArrayList<Floor>> floorsList = service.getFloorsList();

        floorsList.enqueue(new Callback<ArrayList<Floor>>() {
            @Override
            public void onResponse(Call<ArrayList<Floor>> call, Response<ArrayList<Floor>> response) {
                ArrayList floorsDetailList = response.body();
                mAdapter.setFloorsList(floorsDetailList);
                Log.i("responseCheckFloor", "onResponse(): " + "call: " + call + " response: " + response);
            }

            @Override
            public void onFailure(Call<ArrayList<Floor>> call, Throwable t) {
                Log.i("responseCheckFloor", "onFailure(): " + "call: " + call + " t: " + t);
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipeRefreshFloors);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<ArrayList<Floor>> floorsList = service.getFloorsList();

                floorsList.enqueue(new Callback<ArrayList<Floor>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Floor>> call, Response<ArrayList<Floor>> response) {
                        ArrayList floorsDetailList = response.body();
                        mAdapter.setFloorsList(floorsDetailList);
                        swipeRefreshLayout.setRefreshing(false);
                        Log.i("responseCheckFloor", "onResponse(): " + "call: " + call + " response: " + response);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Floor>> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                        Log.i("responseCheckFloor", "onFailure(): " + "call: " + call + " t: " + t);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        getMenuInflater().inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navigation_add) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.new_dialouge, null);
            builder.setView(view);
            final EditText editText = new EditText(MainActivity.this);
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setMessage("Enter Floor Name").setTitle("Enter Floor here");
            builder.setView(editText);
            builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Call<Floor> SetFloor = service.addFloors(editText.getText().toString());
                    SetFloor.enqueue(new Callback<Floor>() {
                        @Override
                        public void onResponse(Call<Floor> call, Response<Floor> response) {
                            Log.i("responseAddFloor", "onResponse(): call: " + call + ", " + "response: " + response);
                        }

                        @Override
                        public void onFailure(Call<Floor> call, Throwable t) {
                            Log.i("responseAddFloor", "onFailure(): call: " + call + ", " + "t: " + t);
                        }
                    });
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.img_dialogue, null);
                    builder.setView(view);
                    AlertDialog dialog1 = builder.create();
                    dialog1.show();*/
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
        } else if (id == R.id.navigation_logout) {
            Retrofit retrofit = RetrofitClient.getRetrofit();
            final LaravelAPI service = retrofit.create(LaravelAPI.class);
            Call<User> logout = service.logoutUser();

            logout.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    sharedPreferences.edit().clear().apply();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectImg1(View view) {
        String name = getResources().getResourceEntryName(R.drawable.icons8_1_48);
        Toast.makeText(this, "" + name, Toast.LENGTH_SHORT).show();
    }

    public void selectImg2 (View view) {
        String name = getResources().getResourceEntryName(R.drawable.icons8_2_48);
        Toast.makeText(this, " " + name, Toast.LENGTH_SHORT).show();
    }

    public void selectImg3 (View view) {
        String name = getResources().getResourceEntryName(R.drawable.icons8_3_48);
        Toast.makeText(this, " " + name, Toast.LENGTH_SHORT).show();
    }
}
