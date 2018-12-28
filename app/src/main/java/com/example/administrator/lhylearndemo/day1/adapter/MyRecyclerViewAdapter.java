package com.example.administrator.lhylearndemo.day1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.lhylearndemo.R;
import com.example.administrator.lhylearndemo.day1.broadcast.activity.BroadcastTestActivity;
import com.example.administrator.lhylearndemo.day1.activity.ChatActivity;
import com.example.administrator.lhylearndemo.day1.activity.TextFragmentActivity;
import com.example.administrator.lhylearndemo.day12_28_view.MyViewActivity;
import com.example.administrator.lhylearndemo.day5_service.activity.ServiceTextActivity;
import com.example.administrator.lhylearndemo.day6_downtest.DwonloadActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private String [] mDataList;

    public MyRecyclerViewAdapter(Context context,String[] dataList){
        this.mContext=context;
        this.mDataList=dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.textView=view.findViewById(R.id.textview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textView.setText(mDataList[position]);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击了第"+position+1+"项",Toast.LENGTH_LONG).show();
                switch (position){
                    case 0:
                        mContext.startActivity(new Intent(mContext,ChatActivity.class));
                        break;
                    case 1:
                        mContext.startActivity(new Intent(mContext,TextFragmentActivity.class));
                        break;
                    case 2:
                        mContext.startActivity(new Intent(mContext,BroadcastTestActivity.class));
                        break;
                    case 3:
                        mContext.startActivity(new Intent(mContext,ServiceTextActivity.class));
                        break;
                    case 4:
                        mContext.startActivity(new Intent(mContext,DwonloadActivity.class));
                        break;
                    case 5:
                        mContext.startActivity(new Intent(mContext,MyViewActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.length;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private AppCompatTextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
