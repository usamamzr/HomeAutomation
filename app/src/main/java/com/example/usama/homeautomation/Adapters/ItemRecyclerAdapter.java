package com.example.usama.homeautomation.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.usama.homeautomation.Models.TblItem;
import com.example.usama.homeautomation.Models.Thing;
import com.example.usama.homeautomation.R;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder> {

    List<TblItem> arrayList;
    private Context context;
    private String RoomId;
    private List<Thing> Itemlistnew;



    public ItemRecyclerAdapter(List<TblItem> arrayList, Context context, int roomId) {
        this.arrayList = arrayList;
        this.context=context;
        this.RoomId=String.valueOf(roomId);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    public void setItemList(List<TblItem> itemList) {
        this.arrayList=itemList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox = itemView.findViewById(R.id.checkBox);

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull ItemRecyclerAdapter.ViewHolder viewHolder, int i) {
       viewHolder.checkBox.setText(arrayList.get(i).getLabel());

       viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

               if(viewHolder.checkBox.isChecked()){
                   Toast.makeText(context,arrayList.get(i).getName()+" isChecked()", Toast.LENGTH_SHORT).show();

                   Thing itemnew = new Thing();
                   itemnew.setTName(arrayList.get(i).getName());
                   itemnew.setRoomId(RoomId);

                   Itemlistnew.add(itemnew);
               }
               if(!viewHolder.checkBox.isChecked()){
                   Toast.makeText(context,arrayList.get(i).getName()+" isUnChecked()", Toast.LENGTH_SHORT).show();

                   Thing itemnew = new Thing();
                   itemnew.setTName(arrayList.get(i).getName());
                   itemnew.setRoomId(RoomId);

                   Itemlistnew.remove(itemnew);
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



}
