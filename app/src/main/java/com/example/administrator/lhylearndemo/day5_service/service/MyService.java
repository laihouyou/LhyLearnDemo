package com.example.administrator.lhylearndemo.day5_service.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private MyDownBinder myDownBinder=new MyDownBinder();
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("tag","服务已经创建 +++++   onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("tag","服务已经启动 +++++   onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("tag","服务已经注销 +++++   onDestroy");
    }

    public class MyDownBinder extends Binder{
        public void startDown(){
            Log.i("tag","已经开始下载");
        }

        public int getDownProgress(){
            Log.i("tag","正在获取下载进度");
            return 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return myDownBinder;
    }
}
