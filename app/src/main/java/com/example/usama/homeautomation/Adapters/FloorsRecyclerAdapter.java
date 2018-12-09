package com.example.usama.homeautomation.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usama.homeautomation.Activities.RoomsActivity;
import com.example.usama.homeautomation.Models.Floor;
import com.example.usama.homeautomation.R;

import java.util.ArrayList;
import java.util.List;

public class FloorsRecyclerAdapter extends RecyclerView.Adapter<FloorsRecyclerAdapter.MyViewHolder> {

    private List<Floor> listItems;
    private Context context;

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_NameFloor;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_NameFloor = itemView.findViewById(R.id.tv_nameFloor);
        }
    }

    public void setFloorsList (ArrayList<Floor> arrayList) {
        this.listItems = arrayList;
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
                intent.putExtra("floorId",listItems.get(position).getId());
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
