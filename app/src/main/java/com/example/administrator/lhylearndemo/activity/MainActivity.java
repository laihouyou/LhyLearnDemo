package com.example.administrator.lhylearndemo.activity;

import android.os.Bundle;

import com.example.administrator.lhylearndemo.R;
import com.example.administrator.lhylearndemo.adapter.MyRecyclerViewAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private String [] mDataList={"recycleview制作聊天界面","fragment动态加载，返回栈"};
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    private void initview() {
        recyclerView=findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        MyRecyclerViewAdapter adapter=new MyRecyclerViewAdapter(this,mDataList);
        recyclerView.setAdapter(adapter);
    }
}
