package com.example.usama.homeautomation.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usama.homeautomation.API.OpenhabAPI;
import com.example.usama.homeautomation.Activities.FloorsActivity;
import com.example.usama.homeautomation.Models.Thing;
import com.example.usama.homeautomation.Models.TblItem;
import com.example.usama.homeautomation.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThingsRecyclerAdapter extends RecyclerView.Adapter<ThingsRecyclerAdapter.MyViewHolder> {
    private List<TblItem> listItems;
    private Context context;
    OpenhabAPI service = OpenhabAPI.retrofit.create(OpenhabAPI.class);


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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.tv_NameThing.getText().equals("Camera")) {
                    Intent intent = new Intent(view.getContext(), FloorsActivity.class);
                    view.getContext().startActivity(intent);
                }
            }
        });

        holder.switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                context = compoundButton.getContext();


                if (b) {
                    Toast.makeText(context, "Checked: "+listItems.get(position).getLabel(), Toast.LENGTH_LONG).show();

                    Call<Void> call = service.setItemState(listItems.get(position).getName().toString(), "ON");
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.i("StateChngResponse", "onResponseON: " + call + " Response: " + response);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.i("StateChngResponse", "onFailureON: " + t);
                        }
                    });
                } else {
                    Toast.makeText(context, "Unchecked: "+listItems.get(position).getLabel(), Toast.LENGTH_LONG).show();
                    Call<Void> SetState = service.setItemState(listItems.get(position).getLabel().toString(),"OFF");
                    SetState.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.i("StateChngResponse", "onResponseOFF: "+call +" Response: "+response);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.i("StateChngResponse", "onFailureOFF: "+t);
                        }
                    });
                }



            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
