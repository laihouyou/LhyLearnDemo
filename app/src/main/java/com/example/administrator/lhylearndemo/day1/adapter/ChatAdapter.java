package com.example.administrator.lhylearndemo.day1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.lhylearndemo.R;
import com.example.administrator.lhylearndemo.day1.vo.ChatVo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<ChatVo> mDataList;

    public ChatAdapter(Context context,List<ChatVo> chatVos){
        this.mContext=context;
        this.mDataList=chatVos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 1:
                View parentView=LayoutInflater.from(mContext).inflate(R.layout.msg_item_recyclerview_left,parent,false);
                LeftViewHolder viewHolder=new LeftViewHolder(parentView);
                return viewHolder;
            case 2:
                View rightparentView=LayoutInflater.from(mContext).inflate(R.layout.msg_item_recyclerview_right,parent,false);
                RightViewHolder rightViewHolder=new RightViewHolder(rightparentView);
                return rightViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ChatVo chatVo=mDataList.get(position);
        if (holder instanceof LeftViewHolder){
            ((LeftViewHolder)holder).leftButton.setText(chatVo.getData());
            ((LeftViewHolder)holder).leftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"你点击了左边第"+position+"条聊天记录",Toast.LENGTH_LONG).show();
                }
            });
        }else if (chatVo.getDataType()==2){
            ((RightViewHolder)holder).rightButton.setText(chatVo.getData());
            ((RightViewHolder)holder).rightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"你点击了右边第"+position+"条聊天记录",Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getDataType();
    }

    class LeftViewHolder extends RecyclerView.ViewHolder{
        private AppCompatButton leftButton;
        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            leftButton=itemView.findViewById(R.id.left_button);
        }
    }
    class RightViewHolder extends RecyclerView.ViewHolder{
        private AppCompatButton rightButton;
        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
            rightButton=itemView.findViewById(R.id.right_button);
        }
    }
}
