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
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<String[]> mDataset;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mName;
        public ImageView mPicture;
        public MyViewHolder(View v) {
            super(v);
            mName=itemView.findViewById(R.id.name);
            mPicture=itemView.findViewById(R.id.image);
        }
    }

    public MyAdapter(ArrayList<String[]> myDataset, Context context) {
        mDataset = myDataset;
        mContext=context;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View view ;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_task_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if(position==getItemCount()-1) {
            holder.mPicture.setImageResource(R.drawable.ic_add_circle_black_24dp);
            holder.mName.setText("Add More");
        } else{
            holder.mName.setText(mDataset.get(position)[0]);
            holder.mPicture.setImageResource(R.drawable.chatbot);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.getAdapterPosition()==getItemCount()-1) {
                    Intent i=new Intent(mContext,NewCaretaker.class);
                    String strName = null;
                    String strContact = null;
                    String strRelation = null;
                    i.putExtra("name", strName);
                    i.putExtra("contact",strContact);
                    i.putExtra("relation",strRelation);
                    mContext.startActivity(i);
                } else {

                }
                Log.e("log", String.valueOf(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
