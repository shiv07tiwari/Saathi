package com.example.shivansh.krishi_care;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<ToDoBody> mDataset;

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

    public MyAdapter(ArrayList<ToDoBody> myDataset) {
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
        holder.mTaskName.setText(mDataset.get(position).getName());
        holder.mTaskDesc.setText(mDataset.get(position).getDate());
        holder.mPriority.setText(mDataset.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}