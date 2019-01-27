package com.example.shivansh.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    private ArrayList<ToDoBody> mDataset;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mTaskName;
        public TextView mTaskDesc;
        public TextView mPriority;
        public MyViewHolder(View v) {
            super(v);
            mTaskName=itemView.findViewById(R.id.task_name);
            mTaskDesc=itemView.findViewById(R.id.task_description);
            mPriority=itemView.findViewById(R.id.task_priority);
        }
    }

    public MyAdapter2(ArrayList<ToDoBody> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view ;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_task_view2, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mTaskName.setText(mDataset.get(position).getName());
        holder.mTaskDesc.setText(mDataset.get(position).getTime());
        holder.mPriority.setText(mDataset.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
