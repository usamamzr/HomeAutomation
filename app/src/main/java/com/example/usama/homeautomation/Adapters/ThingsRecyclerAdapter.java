package com.example.usama.homeautomation.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.usama.homeautomation.Models.Thing;
import com.example.usama.homeautomation.Models.TblItem;
import com.example.usama.homeautomation.R;

import java.util.ArrayList;
import java.util.List;

public class ThingsRecyclerAdapter extends RecyclerView.Adapter<ThingsRecyclerAdapter.MyViewHolder> {
    private List<TblItem> listItems;
    private Context context;

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_NameThing, tv_value;
        Switch switch2;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_NameThing = itemView.findViewById(R.id.tv_nameThing);
            tv_value = itemView.findViewById(R.id.tv_value);
            switch2 = itemView.findViewById(R.id.switch2);
        }
    }

    public ThingsRecyclerAdapter(List<TblItem> arrayList) {
        this.listItems = arrayList;
        notifyDataSetChanged();
    }

    public void setThinglist(List<TblItem> arrayList) {
        this.listItems = arrayList;
        notifyDataSetChanged();
    }


    @Override
    public ThingsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_thing, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_NameThing.setText(this.listItems.get(position).getLabel());

        if (listItems.get(position).getType().equals("Number")) {
            holder.switch2.setVisibility(View.GONE);
            holder.tv_value.setVisibility(View.VISIBLE);
            holder.tv_value.setText(this.listItems.get(position).getState());
        }
        else{
            holder.tv_value.setVisibility(View.GONE);
            holder.switch2.setVisibility(View.VISIBLE);
            if(listItems.get(position).getState().equals("OFF")){
                holder.switch2.setChecked(false);
            }
            else{
                holder.switch2.setChecked(true);

            }

        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
