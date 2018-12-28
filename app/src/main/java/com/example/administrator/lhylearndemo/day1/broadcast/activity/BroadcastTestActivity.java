package com.example.administrator.lhylearndemo.day1.broadcast.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.lhylearndemo.R;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BroadcastTestActivity extends BaseActivity {
    String acion="jljlkjlj.ljljljlj.llllll";
    IntentFilter intentFilter;
    MyBroadcastReceiver broadcastReceiver;
    LocalBroadcastManager localBroadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_test);
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        intentFilter=new IntentFilter();
        intentFilter.addAction(acion);
        broadcastReceiver=new MyBroadcastReceiver();
        localBroadcastManager.registerReceiver(broadcastReceiver,intentFilter);

        Intent intent=new Intent(acion);
        localBroadcastManager.sendBroadcast(intent);

        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent1=new Intent(xiaxianAcon);
                    sendBroadcast(intent1);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }

    class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if (action.equals(acion)){
                Toast.makeText(BroadcastTestActivity.this,"使用了本地广播",Toast.LENGTH_LONG).show();
//                ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//                NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
//                if (networkInfo!=null&&networkInfo.isAvailable()){
//                    Toast.makeText(BroadcastTestActivity.this,"当前网络已连接",Toast.LENGTH_LONG).show();
//                }else {
//                    Toast.makeText(BroadcastTestActivity.this,"当前网络未连接，请检查设置",Toast.LENGTH_LONG).show();
//                }
            }
        }
    }
}
