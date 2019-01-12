package com.example.usama.homeautomation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.usama.homeautomation.API.LaravelAPI;
import com.example.usama.homeautomation.API.OpenhabAPI;
import com.example.usama.homeautomation.API.RetrofitClient;
import com.example.usama.homeautomation.Adapters.ItemRecyclerAdapter;
import com.example.usama.homeautomation.Models.TblItem;
import com.example.usama.homeautomation.Models.Thing;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyDialogFragment extends DialogFragment {
    private RecyclerView mRecyclerView;
    private ItemRecyclerAdapter mAdapter;

    private int RoomId;
    private List<TblItem> itemList = new ArrayList<>();
    private List<TblItem> itemListnew = new ArrayList<>();
    private List<Thing> itemlistnew = new ArrayList<>();
    private ArrayList<Thing> getitemlist = new ArrayList<>();


    Retrofit retrofit = RetrofitClient.getRetrofit();
    LaravelAPI service = retrofit.create(LaravelAPI.class);

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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mRecyclerView = new RecyclerView(getContext());

        if (getArguments() != null) {

            RoomId = getArguments().getInt("RoomId");
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ItemRecyclerAdapter(itemList, getContext(), RoomId);
        mRecyclerView.setAdapter(mAdapter);

        Call<ArrayList<Thing>> thingslist = service.getThingsList();
        thingslist.enqueue(new Callback<ArrayList<Thing>>() {
            @Override
            public void onResponse(Call<ArrayList<Thing>> call, Response<ArrayList<Thing>> response) {
                getitemlist = response.body();

                Log.i("onRespDialogThings", "onResponse: " + call + " t: " + response);
                Log.i("getitemlist", "onResponse: " + getitemlist.size());

                OpenhabAPI service2 = OpenhabAPI.retrofit.create(OpenhabAPI.class);
                Call<List<TblItem>> ItemList = service2.getItemList(getAuthToken());
                ItemList.enqueue(new Callback<List<TblItem>>() {
                    @Override
                    public void onResponse(Call<List<TblItem>> call, Response<List<TblItem>> response) {
                        itemList = response.body();
                        if (getitemlist.size() == 0) {

                        } else {

                            for (int i = itemList.size() - 1; i >= 0; i--) {
                                int j = getitemlist.size() - 1;
                                boolean check = false;
                                do {
                                    if (itemList.get(i).getName().equals(getitemlist.get(j).getTName())) {
                                        j--;
                                        check = true;
                                    } else {
                                        j--;
                                    }
                                } while (j >= 0);
                                if (check == true) {
                                    itemList.remove(i);
                                }
                            }
                        }
                        mAdapter.setItemList(itemList);
                        Log.i("responseCheckItems", "onResponse(): " + "call: " + call + " response: " + response);
                    }

                    @Override
                    public void onFailure(Call<List<TblItem>> call, Throwable t) {
                        Log.i("responseCheckItems", "onFailure(): " + "call: " + call + " t: " + t);
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Thing>> call, Throwable t) {
                Log.i("onFailDialogThings", "onFailure: " + call + " t: " + t);
            }
        });


        return new AlertDialog.Builder(getActivity())
                .setTitle("Available Things")
                .setView(mRecyclerView)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                itemlistnew = mAdapter.getItemList();

                                Toast.makeText(getContext(), "" + itemlistnew.size(), Toast.LENGTH_SHORT).show();
                                for (int i = itemlistnew.size() - 1; i >= 0; i--) {
                                    Call<Thing> postThings = service.addThings(itemlistnew.get(i).getTName(), itemlistnew.get(i).getRoomId());
                                    postThings.enqueue(new Callback<Thing>() {
                                        @Override
                                        public void onResponse(Call<Thing> call, Response<Thing> response) {
                                            Log.i("responseCheckThingss", "onResponse(): " + "call: " + call + " response: " + response);
                                        }

                                        @Override
                                        public void onFailure(Call<Thing> call, Throwable t) {
                                            Log.i("responseCheckThingss", "onFailure(): " + "call: " + call + " t: " + t);
                                        }
                                    });
                                }
                            }
                        }
                ).create();
    }
}
