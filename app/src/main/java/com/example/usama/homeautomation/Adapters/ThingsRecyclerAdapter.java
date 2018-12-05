package com.example.usama.homeautomation.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usama.homeautomation.R;

public class ThingsRecyclerAdapter extends RecyclerView.Adapter<ThingsRecyclerAdapter.MyViewHolder> {
    private String[] mDataset;

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        MyViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    public ThingsRecyclerAdapter(String[] myDataset) {
        mDataset = myDataset;
    }


    @Override
    public ThingsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_floor, parent, false);
        ThingsRecyclerAdapter.MyViewHolder vh = new ThingsRecyclerAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ThingsRecyclerAdapter.MyViewHolder holder, int position) {

        holder.mTextView.setText(mDataset[position]);

    }

    @Override
    public int getItemCount() {
//        return mDataset.length;
        return 0;
    }
}
