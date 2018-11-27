package com.example.administrator.lhylearndemo.day5_service.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.administrator.lhylearndemo.R;
import com.example.administrator.lhylearndemo.day5_service.service.MyService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class ServiceTextActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton startButton;
    private AppCompatButton stopButton;
    private AppCompatButton bindButton;
    private AppCompatButton unBindButton;

    private MyService.MyDownBinder myDownBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_text);
        initView();
    }

    private void initView() {
        startButton=findViewById(R.id.startService);
        stopButton=findViewById(R.id.stopService);
        bindButton=findViewById(R.id.bindService);
        unBindButton=findViewById(R.id.unBindService);

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        bindButton.setOnClickListener(this);
        unBindButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startService:
                Intent intent=new Intent(ServiceTextActivity.this,MyService.class);
                startService(intent);
                break;
            case R.id.stopService:
                Intent intent1=new Intent(ServiceTextActivity.this,MyService.class);
                stopService(intent1);
                break;
            case R.id.bindService:
                Intent intent2=new Intent(ServiceTextActivity.this,MyService.class);
                bindService(intent2,serviceConnection,BIND_AUTO_CREATE);
                break;
            case R.id.unBindService:
                unbindService(serviceConnection);
                break;
        }
    }

    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myDownBinder= (MyService.MyDownBinder) service;
            myDownBinder.startDown();
            myDownBinder.getDownProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
