package com.example.administrator.lhylearndemo.day6_downtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import com.example.administrator.lhylearndemo.R;

import java.io.File;

import androidx.core.app.NotificationCompat;

public class DwonloadService extends Service {
    private DwonloadBudle budle=new DwonloadBudle();
    private DownloadAsyn downloadAsyn;
    private String dwonloadUrl;
    private Handler myHandler;

    String NOTIFICATION_CHANNEL_ID = "com.example.lhy";
    String channelName = "My Dwonload Service";

    public DwonloadService() {
    }

    private DownloadLinstener downloadLinstener=new DownloadLinstener() {
        @Override
        public void onProgess(int progess) {
            getNotificationManager().notify(10,getNotification(getString(R.string.msg10),progess));
            Message message=new Message();
            message.what=Contest.TYPE_DWONLOADING;
            Bundle bundle=new Bundle();
            bundle.putInt("progess",progess);
            message.setData(bundle);
            myHandler.sendMessage(message);
        }

        @Override
        public void onSuccess() {
            downloadAsyn=null;
            stopForeground(true);
            getNotificationManager().notify(10,getNotification(getString(R.string.msg11),-1));
            Toast.makeText(DwonloadService.this,getString(R.string.msg11),Toast.LENGTH_LONG).show();
            Message message=new Message();
            message.what=Contest.TYPE_SUCCESS;
            myHandler.sendMessage(message);
        }

        @Override
        public void onFailed() {
            downloadAsyn=null;
            stopForeground(true);
            getNotificationManager().notify(10,getNotification(getString(R.string.msg12),-1));
            Toast.makeText(DwonloadService.this,getString(R.string.msg12),Toast.LENGTH_LONG).show();
            Message message=new Message();
            message.what=Contest.TYPE_FAILED;
            myHandler.sendMessage(message);
        }

        @Override
        public void onPaused() {
            downloadAsyn=null;
            Toast.makeText(DwonloadService.this,getString(R.string.msg13),Toast.LENGTH_LONG).show();
            Message message=new Message();
            message.what=Contest.TYPE_PAUSED;
            myHandler.sendMessage(message);
        }

        @Override
        public void onCanceled() {
            downloadAsyn=null;
            stopForeground(true);
            getNotificationManager().notify(10,getNotification(getString(R.string.msg14),-1));
            Toast.makeText(DwonloadService.this,getString(R.string.msg14),Toast.LENGTH_LONG).show();
            Message message=new Message();
            message.what=Contest.TYPE_CANCALED;
            myHandler.sendMessage(message);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_LOW);

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager == null)
                return;
            manager.createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                    .setAutoCancel(true)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setOngoing(true)
                    .setPriority(NotificationManager.IMPORTANCE_LOW)
                    .build();

            startForeground(1, notification);

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return budle;
    }

    class DwonloadBudle extends Binder {
        public void startDwonload(String url, Handler handler){
            if (downloadAsyn==null){
                dwonloadUrl=url;
                myHandler=handler;
                downloadAsyn=new DownloadAsyn(downloadLinstener);
                downloadAsyn.execute(url);
                startForeground(1,getNotification(getString(R.string.msg5),0));
                Toast.makeText(DwonloadService.this,getText(R.string.msg5),Toast.LENGTH_LONG).show();
            }
        }

        public void pausedDwonload(){
            if (downloadAsyn!=null){
                downloadAsyn.setPaused(true);
            }
        }
        public void canceledDwonload(){
            if (downloadAsyn!=null){
                downloadAsyn.setCancaled(true);
            }else {
                String downFileName=dwonloadUrl.substring(dwonloadUrl.lastIndexOf("/"));
                String directoy=Environment
                        .getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOWNLOADS).getPath();
                File file=new File(directoy+downFileName);
                if (file.exists()){
                    file.delete();
                }
                getNotificationManager().cancel(10);
                stopForeground(true);
                Toast.makeText(DwonloadService.this,getText(R.string.msg14),Toast.LENGTH_LONG).show();
            }
        }

    }

    private NotificationManager getNotificationManager(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager == null)
                return  null;
            manager.createNotificationChannel(channel);
            return manager;
        }else {
            return (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    private Notification getNotification(String title, int progess){
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setChannelId(NOTIFICATION_CHANNEL_ID);
        }
        return notification.build();
    }
}
