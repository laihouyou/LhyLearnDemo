package com.example.administrator.lhylearndemo.broadcast.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.administrator.lhylearndemo.broadcast.utils.ActivityUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    String xiaxianAcon="ljlj.ljlkjsgfr.ljljlkcd.kjljljl.df";
    XiaxianBroadcast xiaxianBroadcast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.AddActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.RemoveActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(xiaxianAcon);
        xiaxianBroadcast =new XiaxianBroadcast();
        registerReceiver(xiaxianBroadcast,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (xiaxianBroadcast!=null){
            unregisterReceiver(xiaxianBroadcast);
        }
        xiaxianBroadcast=null;
    }

    class XiaxianBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(final Context context, Intent intent) {
            String action=intent.getAction();
            if (action.equals(xiaxianAcon)){
                AlertDialog.Builder builder=new AlertDialog.Builder(context)
                        .setCancelable(false)
                        .setTitle("提示")
                        .setMessage("您的账号存在安全问题，已为您下线处理")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityUtils.RemoverAllActivity();
                                Intent intent1=new Intent(context,LoginActivity.class);
                                startActivity(intent1);
                            }
                        });

                builder.create().show();

            }
        }
    }
}
