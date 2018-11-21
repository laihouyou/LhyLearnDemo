package com.example.administrator.lhylearndemo.activity;

import android.os.Bundle;

import com.example.administrator.lhylearndemo.R;
import com.example.administrator.lhylearndemo.adapter.ChatAdapter;
import com.example.administrator.lhylearndemo.vo.ChatVo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ChatVo> chatVoList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        chatVoList=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ChatVo chatVo=new ChatVo();
            if (i/2==0){
                chatVo.setData("我说：现在是第"+i+1+"条信息");
                chatVo.setDataType(1);
            }else {
                chatVo.setData("好友说：我知道了,我在回复第"+i+1+"条信息");
                chatVo.setDataType(2);
            }
            chatVoList.add(chatVo);
        }
    }

    private void initView() {
        recyclerView=findViewById(R.id.recyclerview);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ChatAdapter chatAdapter=new ChatAdapter(this,chatVoList);
        recyclerView.setAdapter(chatAdapter);
    }
}
