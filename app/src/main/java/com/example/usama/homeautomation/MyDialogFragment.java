package com.example.usama.homeautomation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.usama.homeautomation.API.OpenhabAPI;
import com.example.usama.homeautomation.Adapters.ItemRecyclerAdapter;
import com.example.usama.homeautomation.Adapters.RoomsRecyclerAdapter;
import com.example.usama.homeautomation.Models.Room;
import com.example.usama.homeautomation.Models.TblItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDialogFragment extends DialogFragment {
    private RecyclerView mRecyclerView;
    private ItemRecyclerAdapter mAdapter;

    private int RoomId;
    private List<TblItem> itemList= new ArrayList<>();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mRecyclerView = new RecyclerView(getContext());

        if (getArguments() != null) {

           RoomId = getArguments().getInt("RoomId");
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ItemRecyclerAdapter(itemList,getContext(),RoomId);
        mRecyclerView.setAdapter(mAdapter);

        OpenhabAPI service2 = OpenhabAPI.retrofit.create(OpenhabAPI.class);
        Call<List<TblItem>> ItemList = service2.getItemList();
        ItemList.enqueue(new Callback<List<TblItem>>() {
            @Override
            public void onResponse(Call<List<TblItem>> call, Response<List<TblItem>> response) {
                itemList = response.body();
                mAdapter.setItemList(itemList);
                Log.i("responseCheckItems", "onResponse(): " + "call: " + call + " response: " + response);
            }

            @Override
            public void onFailure(Call<List<TblItem>> call, Throwable t) {
                Log.i("responseCheckItems", "onFailure(): " + "call: " + call + " t: " + t);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setTitle("Available Things")
                .setView(mRecyclerView)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // do something
                            }
                        }
                ).create();
    }
}
