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
import com.example.usama.homeautomation.Activities.ThingsActivity;
import com.example.usama.homeautomation.Models.Room;
import com.example.usama.homeautomation.R;
import com.example.usama.homeautomation.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RoomsRecyclerAdapter extends RecyclerView.Adapter<RoomsRecyclerAdapter.MyViewHolder> {
    private List<Room> listItems;
    private Context context;
    private String string;

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_nameRoom;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_nameRoom = itemView.findViewById(R.id.tv_nameRoom);
        }
    }

    public RoomsRecyclerAdapter(ArrayList<Room> arrayList) {
        this.listItems = arrayList;
    }

    public void setRoomList(ArrayList<Room> arrayList) {
        this.listItems = arrayList;
        notifyDataSetChanged();
    }


    @Override
    public RoomsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_room, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_nameRoom.setText(this.listItems.get(position).getRName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ThingsActivity.class);
                intent.putExtra("roomId", listItems.get(position).getId());
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
                        Integer roomID = listItems.get(position).getId();

                        Retrofit retrofit = RetrofitClient.getRetrofit();
                        LaravelAPI service = retrofit.create(LaravelAPI.class);
                        Call<String> getThingByRoomId = service.getThing(roomID);

                        getThingByRoomId.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                string = response.body();
                                Log.i("responseThingByRoom", "onResponse(): " + "call: " + call + " response: " + response + "string: " + string);
                                if (string != null && string.equals("0")) {
                                    Retrofit retrofit1 = RetrofitClient.getRetrofit();
                                    LaravelAPI service1 = retrofit1.create(LaravelAPI.class);
                                    Call<Room> deleteRoom = service1.deleteRoom(roomID);

                                    deleteRoom.enqueue(new Callback<Room>() {
                                        @Override
                                        public void onResponse(Call<Room> call, Response<Room> response) {
                                            Log.i("responseDeleteRoom", "onResponse(): " + "call: " + call + " response: " + response);
                                            Toast.makeText(view.getContext(), "Room deleted", Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onFailure(Call<Room> call, Throwable t) {
                                            Log.i("responseDeleteRoom", "onFailure(): " + "call: " + call + " t: " + t);
                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(context, "Cannot Delete the Floor as it contains Things", Toast.LENGTH_SHORT).show();
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
