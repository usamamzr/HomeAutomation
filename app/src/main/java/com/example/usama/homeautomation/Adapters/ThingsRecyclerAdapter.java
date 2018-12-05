package com.example.usama.homeautomation.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usama.homeautomation.Models.Thing;
import com.example.usama.homeautomation.R;

import java.util.ArrayList;
import java.util.List;

public class ThingsRecyclerAdapter extends RecyclerView.Adapter<ThingsRecyclerAdapter.MyViewHolder> {
    private List<Thing> listItems;
    private Context context;

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_NameThing;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_NameThing = itemView.findViewById(R.id.tv_nameFloor);
        }
    }

    public ThingsRecyclerAdapter(ArrayList<Thing> arrayList) {
        this.listItems = arrayList;
    }


    @Override
    public ThingsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_thing, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_NameThing.setText(this.listItems.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
