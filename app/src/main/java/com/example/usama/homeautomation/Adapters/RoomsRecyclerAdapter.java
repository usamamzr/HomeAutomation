package com.example.usama.homeautomation.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usama.homeautomation.Activities.ThingsActivity;
import com.example.usama.homeautomation.Models.Room;
import com.example.usama.homeautomation.R;

import java.util.ArrayList;
import java.util.List;

public class RoomsRecyclerAdapter extends RecyclerView.Adapter<RoomsRecyclerAdapter.MyViewHolder> {
    private List<Room> listItems;
    private Context context;

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

    public void setRoomList (ArrayList<Room> arrayList){
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
                intent.putExtra("roomId",listItems.get(position).getId());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
