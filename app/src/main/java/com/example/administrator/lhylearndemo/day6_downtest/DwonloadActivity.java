package com.example.administrator.lhylearndemo.day6_downtest;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.lhylearndemo.R;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class DwonloadActivity extends AppCompatActivity  implements View.OnClickListener,DownloadLinstener {
    private AppCompatTextView progressText;
    private ProgressBar progressBar;

    private DwonloadService.DwonloadBudle budle;

    private static final String url="https://dl.google.com/dl/android/studio/install/3.2.0.26/android-studio-ide-181.5014246-windows.exe";
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dwonload);
        context=this;
        initView();
        initPamean();
        initSrevier();
    }

    private void initSrevier() {
        Intent intent=new Intent(this,DwonloadService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    private void initPamean() {
        if (ContextCompat.checkSelfPermission
                (this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this
                    ,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
        }
        if (ContextCompat.checkSelfPermission
                (this,Manifest.permission.INTERNET)
                !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this
                    ,new String[]{Manifest.permission.INTERNET},11);
        }
    }

    private void initView() {
        AppCompatButton startDwonload=findViewById(R.id.startDwonload);
        AppCompatButton pausedDwonload=findViewById(R.id.pausedDwonload);
        AppCompatButton canceledDwonload=findViewById(R.id.canceledDwonload);
        startDwonload.setOnClickListener(this);
        pausedDwonload.setOnClickListener(this);
        canceledDwonload.setOnClickListener(this);
        progressText=findViewById(R.id.progressText);
        progressBar=findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startDwonload:
//                Notification notification=getNotification("正在下载",-1);
//                getNotificationManager().notify(1,notification);
                budle.startDwonload(url,this);
                break;
            case R.id.pausedDwonload:
                budle.pausedDwonload();
                break;
            case R.id.canceledDwonload:
                budle.canceledDwonload();
                break;
        }
    }

    @Override
    public void onProgess(int progess) {
        progressBar.setProgress(progess);
        progressText.setText(progess+"%");

//        Notification notification=getNotification("下载出错",-progess);
//        getNotificationManager().notify(1,notification);
    }

    @Override
    public void onSuccess() {
//        getNotificationManager().notify(1,getNotification("下载已完成",-1));
        progressText.setText("下载已完成");
    }

    @Override
    public void onFailed() {
        progressText.setText("下载出错");
        budle.onFailed();
//        Notification notification=getNotification("下载出错",-1);
//        getNotificationManager().notify(1,notification);
    }

    @Override
    public void onPaused() {
        progressText.setText("下载已暂停");
    }

    @Override
    public void onCanceled() {
//        getNotificationManager().notify(1,getNotification("下载已取消",-1));
        progressText.setText("下载已取消");

        String downFileName=url.substring(url.lastIndexOf("/"));
        String directoy=Environment
                .getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS).getPath();
        File file=new File(directoy+downFileName);
        if (file.exists()){
            file.delete();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==10&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,getString(R.string.msg8),Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,getString(R.string.msg9),Toast.LENGTH_LONG).show();
        }
        if (requestCode==11&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,getString(R.string.msg8),Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,getString(R.string.msg9),Toast.LENGTH_LONG).show();
        }
    }

    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            budle= (DwonloadService.DwonloadBudle) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    } ;

    private NotificationManager getNotificationManager(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("xxx", "xxx", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager == null)
                return  null;
            manager.createNotificationChannel(channel);
            return manager;
        }else {
            return (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    private Notification getNotification(String title,int progess){
        Intent intent=new Intent(this,DwonloadActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
        Notification.Builder notification=new Notification.Builder(this);
        notification.setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent);
        if (progess>0){
            notification.setContentText(progess+"%");
            notification.setProgress(100,progess,false);
        }

        return notification.build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
