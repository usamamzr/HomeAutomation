package com.example.usama.homeautomation.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usama.homeautomation.API.LaravelAPI;
import com.example.usama.homeautomation.API.OpenhabAPI;
import com.example.usama.homeautomation.Activities.CameraActivity;
import com.example.usama.homeautomation.Models.TblItem;
import com.example.usama.homeautomation.R;
import com.example.usama.homeautomation.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.NOTIFICATION_SERVICE;

public class ThingsRecyclerAdapter extends RecyclerView.Adapter<ThingsRecyclerAdapter.MyViewHolder> {
    private List<TblItem> listItems;
    private Context context;
    OpenhabAPI service = OpenhabAPI.retrofit.create(OpenhabAPI.class);
    Retrofit retrofit = RetrofitClient.getRetrofit();
    LaravelAPI service2 = retrofit.create(LaravelAPI.class);


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
        } else {
            if (listItems.get(position).getLabel().contains("Camera")) {
                holder.tv_value.setVisibility(View.GONE);
                holder.switch2.setVisibility(View.GONE);
            } else {
                holder.tv_value.setVisibility(View.GONE);
                holder.switch2.setVisibility(View.VISIBLE);
                if (listItems.get(position).getState().equals("OFF")) {
                    holder.switch2.setChecked(false);
                } else {
                    holder.switch2.setChecked(true);

                }
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.tv_NameThing.getText().equals("Camera")) {
                    Intent intent = new Intent(view.getContext(), CameraActivity.class);
                    view.getContext().startActivity(intent);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setTitle("Delete");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Call<String> DeleteThing = service2.deleteThing(listItems.get(position).getName().toString());
                        DeleteThing.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Log.i("DeleteResponse", "onResponse: " + call + " Response: " + response);
                                listItems.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(view.getContext(), "Item is Deleted", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.i("DeleteResponse", "onFaliure: " + call + " t: " + t);
                                Toast.makeText(view.getContext(), "Item cant be Deleted, try again Later", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


                return true;
            }
        });

        holder.switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                context = compoundButton.getContext();


                if (b) {
                    Toast.makeText(context, listItems.get(position).getLabel() + " is ON", Toast.LENGTH_LONG).show();

                    if (listItems.get(position).getLabel().contains("Motion")) {

                        notification();
                    }


                } else {
                    Toast.makeText(context, listItems.get(position).getLabel() + " is OFF", Toast.LENGTH_LONG).show();

                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public void notification() {
//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        Notification noti = new Notification.Builder(context)
//                .setContentTitle("Motion Sensor")
//                .setContentText("Motion Detected")
//                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
////                                .setVibrate(new long[]{1000})
////                                .setPriority(NotificationManager.IMPORTANCE_HIGH)
////                                .setSound(uri)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setPriority(Notification.PRIORITY_DEFAULT)
//                .setAutoCancel(false)
//                .build();
//        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//        if (notificationManager != null) {
//            notificationManager.notify(1, noti);


        NotificationCompat.Builder builder =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentTitle("Motion Sensor")
                    .setContentText("Motion Detected!!")
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH).setAutoCancel(false);
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());



    }
}
