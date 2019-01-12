package com.example.usama.homeautomation.Activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.usama.homeautomation.API.LaravelAPI;
import com.example.usama.homeautomation.API.OpenhabAPI;
import com.example.usama.homeautomation.Adapters.ThingsRecyclerAdapter;
import com.example.usama.homeautomation.Models.TblItem;
import com.example.usama.homeautomation.Models.Thing;
import com.example.usama.homeautomation.MyDialogFragment;
import com.example.usama.homeautomation.R;
import com.example.usama.homeautomation.API.RetrofitClient;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ThingsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ThingsRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    SwipeRefreshLayout swipeRefreshLayout;
private Timer timer = new Timer();
    private int RoomId;
    private List<TblItem> itemList = new ArrayList<>();


    public static String getAuthToken(){
        byte[] data = new byte[0];
        try {
            data = ("m.ahmed33@ucp.edu.pk:openhab123").getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_things);
        if (getIntent() != null) {
            RoomId = getIntent().getIntExtra("roomId", 0);
        }

        final ArrayList<Thing> arrayList = new ArrayList<>();

//        for (int i = 1; i <= 10; i++) {
//            arrayList.add(new Thing("Item: " + i));
//        }

        mRecyclerView = findViewById(R.id.things_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ThingsRecyclerAdapter(itemList);
        mRecyclerView.setAdapter(mAdapter);

        Retrofit retrofit = RetrofitClient.getRetrofit();
        LaravelAPI service = retrofit.create(LaravelAPI.class);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Call<ArrayList<Thing>> ThingList = service.getThingByRoom(RoomId);
                ThingList.enqueue(new Callback<ArrayList<Thing>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Thing>> call, Response<ArrayList<Thing>> response) {
                        ArrayList<Thing> things = response.body();

                        Log.i("responseCheckThings", "onResponse(): " + "call: " + call + " response: " + response);
                        Log.i("responsethings", "onResponse: size: " + things.size());

                        OpenhabAPI service2 = OpenhabAPI.retrofit.create(OpenhabAPI.class);
                        Call<List<TblItem>> ItemList = service2.getItemList(getAuthToken());
                        ItemList.enqueue(new Callback<List<TblItem>>() {
                            @Override
                            public void onResponse(Call<List<TblItem>> call, Response<List<TblItem>> response) {
                                itemList = response.body();

                                Log.i("responseCheckItems", "onResponse(): " + "call: " + call + " response: " + response);

                                if (things.size() == 0) {
                                    itemList.removeAll(itemList);

                                } else {

                                    for (int i = itemList.size() - 1; i >= 0; i--) {
                                        int j = things.size() - 1;
                                        boolean check = true;
                                        do {
                                            if (!itemList.get(i).getName().equals(things.get(j).getTName())) {
                                                j--;
                                            } else {
                                                check = false;
                                                j--;
                                            }
                                        } while (j >= 0);
                                        if (check == true) {
                                            itemList.remove(i);
                                        }
                                    }
                                }
                                Log.i("responseitems", "onResponse: size: " + itemList.size());
                                mAdapter.setThinglist(itemList);
                            }

                            @Override
                            public void onFailure(Call<List<TblItem>> call, Throwable t) {
                                Log.i("responseCheckItems", "onFailure(): " + "call: " + call + " t: " + t);
                            }
                        });
                    }


                    @Override
                    public void onFailure(Call<ArrayList<Thing>> call, Throwable t) {
                        Log.i("responseCheckThings", "onFailure(): " + "call: " + call + " t: " + t);
                    }
                });
            }
        }, 1000, 7000);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshThings);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<ArrayList<Thing>> ThingList = service.getThingByRoom(RoomId);
                ThingList.enqueue(new Callback<ArrayList<Thing>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Thing>> call, Response<ArrayList<Thing>> response) {
                        ArrayList<Thing> things = response.body();

                        Log.i("responseCheckThings", "onResponse(): " + "call: " + call + " response: " + response);
                        Log.i("responsethings", "onResponse: size: " + things.size());

                        OpenhabAPI service2 = OpenhabAPI.retrofit.create(OpenhabAPI.class);
                        Call<List<TblItem>> ItemList = service2.getItemList(getAuthToken());
                        ItemList.enqueue(new Callback<List<TblItem>>() {
                            @Override
                            public void onResponse(Call<List<TblItem>> call, Response<List<TblItem>> response) {
                                itemList = response.body();

                                Log.i("responseCheckItems", "onResponse(): " + "call: " + call + " response: " + response);

                                if (things.size() == 0) {
                                    itemList.removeAll(itemList);

                                } else {

                                    for (int i = itemList.size() - 1; i >= 0; i--) {
                                        int j = things.size() - 1;
                                        boolean check = true;
                                        do {
                                            if (!itemList.get(i).getName().equals(things.get(j).getTName())) {
                                                j--;
                                            } else {
                                                check = false;
                                                j--;
                                            }
                                        } while (j >= 0);
                                        if (check == true) {
                                            itemList.remove(i);
                                        }
                                    }
                                }
                                Log.i("responseitems", "onResponse: size: " + itemList.size());
                                mAdapter.setThinglist(itemList);
                                swipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onFailure(Call<List<TblItem>> call, Throwable t) {
                                Log.i("responseCheckItems", "onFailure(): " + "call: " + call + " t: " + t);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Thing>> call, Throwable t) {
                        Log.i("responseCheckThings", "onFailure(): " + "call: " + call + " t: " + t);
                    }
                });
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
            MyDialogFragment myDialogFragment = new MyDialogFragment();
            Bundle data = new Bundle();//Use bundle to pass data
            data.putInt("RoomId", RoomId);//put string, int, etc in bundle with a key value
            myDialogFragment.setArguments(data);
            myDialogFragment.show(getSupportFragmentManager(), "MyFragment");

//// setup the alert builder
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Choose Things");
//
//// add a checkbox list
//            String[] animals = {"horse", "cow", "camel", "sheep", "goat"};
//            boolean[] checkedItems = null; /*{true, false, false, true, false}*/;
//            builder.setMultiChoiceItems(animals, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                    // user checked or unchecked a box
//                }
//            });
//
//// add OK and Cancel buttons
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    // user clicked OK
//                }
//            });
//            builder.setNegativeButton("Cancel", null);
//
//// create and show the alert dialog
//            AlertDialog dialog = builder.create();
//            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        timer.cancel();
        finish();
    }
}
