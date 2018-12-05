package com.example.usama.homeautomation.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usama.homeautomation.Models.Floor;
import com.example.usama.homeautomation.R;

import java.util.ArrayList;
import java.util.List;

public class FloorsRecyclerAdapter extends RecyclerView.Adapter<FloorsRecyclerAdapter.MyViewHolder> {

    private List<Floor> listItems;
    private Context context;

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        MyViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    public FloorsRecyclerAdapter(ArrayList<Floor> arrayList) {
        this.listItems = arrayList;
    }


    @Override
    public FloorsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.single_floor, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mTextView.setText(mDataset[position]);

    }

    @Override
    public int getItemCount() {
//        return mDataset.length;
        return 0;
    }
}
