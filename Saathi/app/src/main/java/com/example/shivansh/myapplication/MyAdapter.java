package com.example.shivansh.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<String[]> mDataset;

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

    public MyAdapter(ArrayList<String[]> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View view ;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_task_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTaskName.setText(mDataset.get(position)[0]);
        holder.mTaskDesc.setText(mDataset.get(position)[1]);
        holder.mPriority.setText(mDataset.get(position)[2]);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
