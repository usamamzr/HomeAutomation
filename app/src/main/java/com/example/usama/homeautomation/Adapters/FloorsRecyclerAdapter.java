package com.example.usama.homeautomation.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usama.homeautomation.API.LaravelAPI;
import com.example.usama.homeautomation.Activities.RoomsActivity;
import com.example.usama.homeautomation.Models.Floor;
import com.example.usama.homeautomation.R;
import com.example.usama.homeautomation.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FloorsRecyclerAdapter extends RecyclerView.Adapter<FloorsRecyclerAdapter.MyViewHolder> {

    private List<Floor> listItems;
    private Context context;
    private String string;

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_NameFloor;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_NameFloor = itemView.findViewById(R.id.tv_nameFloor);
        }
    }

    public void setFloorsList(ArrayList<Floor> arrayList) {
        this.listItems = arrayList;
        this.context = context;
        notifyDataSetChanged();
    }

    public FloorsRecyclerAdapter(ArrayList<Floor> arrayList) {
        this.listItems = arrayList;
    }


    @Override
    public FloorsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_floor, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_NameFloor.setText(this.listItems.get(position).getFName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RoomsActivity.class);
                intent.putExtra("floorId", listItems.get(position).getId());
                view.getContext().startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setTitle("Delete");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Integer floorID = listItems.get(position).getId();

                        Retrofit retrofit = RetrofitClient.getRetrofit();
                        LaravelAPI service = retrofit.create(LaravelAPI.class);
                        Call<String> getFloorByRoomId = service.getRoom(floorID);

                        getFloorByRoomId.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                string = response.body();
                                Log.i("responseFloorByRoom", "onResponse(): " + "call: " + call + " response: " + response + "string: " + string);
                                if (string != null && string.equals("0")) {
                                    Retrofit retrofit1 = RetrofitClient.getRetrofit();
                                    LaravelAPI service1 = retrofit1.create(LaravelAPI.class);
                                    Call<Floor> deleteFloor = service1.deleteFloor(floorID);

                                    deleteFloor.enqueue(new Callback<Floor>() {
                                        @Override
                                        public void onResponse(Call<Floor> call, Response<Floor> response) {
                                            Log.i("responseDeleteFloor", "onResponse(): " + "call: " + call + " response: " + response);
                                            Toast.makeText(view.getContext(), "Floor deleted", Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onFailure(Call<Floor> call, Throwable t) {
                                            Log.i("responseDeleteFloor", "onFailure(): " + "call: " + call + " t: " + t);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.i("responseThingByRoom", "onFailure(): " + "call: " + call + " t: " + t);
                            }
                        });
                        listItems.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
