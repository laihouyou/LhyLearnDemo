package com.example.administrator.lhylearndemo.day1.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.administrator.lhylearndemo.R;
import com.example.administrator.lhylearndemo.day1.adapter.MyRecyclerViewAdapter;
import com.example.administrator.lhylearndemo.day1.broadcast.activity.BaseActivity;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity {
    private String [] mDataList={
            "recycleview制作聊天界面","fragment动态加载，返回栈","广播接收器",
            "服务","完整下载例子","自定义view_onMeasure","view动画","viewPage2增删改查"
    };
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

        //item进场动画
//        LayoutAnimationController animationController=new LayoutAnimationController(
//                );
//        animationController.setDelay(0.5F);
//        animationController.setOrder(LayoutAnimationController.ORDER_RANDOM);
        Animation animation=AnimationUtils.loadAnimation(this,R.anim.item_animation);
        recyclerView.startAnimation(animation);

        MyRecyclerViewAdapter adapter=new MyRecyclerViewAdapter(this,mDataList);
        recyclerView.setAdapter(adapter);
    }
}
