package com.example.administrator.lhylearndemo.day6_downtest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.example.administrator.lhylearndemo.R;

public class DwonloadService extends Service {
    private DwonloadBudle budle=new DwonloadBudle();
    private DownloadAsyn downloadAsyn;

    public DwonloadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            NotificationChannel channel = new NotificationChannel("xxx", "xxx", NotificationManager.IMPORTANCE_LOW);
//
//            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            if (manager == null)
//                return;
//            manager.createNotificationChannel(channel);
//
//            Notification notification = new NotificationCompat.Builder(this, "xxx")
//                    .setAutoCancel(true)
//                    .setCategory(Notification.CATEGORY_SERVICE)
//                    .setOngoing(true)
//                    .setPriority(NotificationManager.IMPORTANCE_LOW)
//                    .build();
//
//            startForeground(101, notification);
//
//        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return budle;
    }

    class DwonloadBudle extends Binder {
        public void startDwonload(String url, DownloadLinstener linstener){
            if (downloadAsyn==null){
                downloadAsyn=new DownloadAsyn(linstener);
                downloadAsyn.execute(url);
                Toast.makeText(getBaseContext(),getText(R.string.msg5),Toast.LENGTH_LONG).show();
//                startForeground(1,notification);
            }
        }

        public void onFailed( ){
//            stopForeground(true);
        }


        public void pausedDwonload(){
            if (downloadAsyn!=null){
                downloadAsyn.setPaused(true);
            }
        }
        public void canceledDwonload(){
            if (downloadAsyn!=null){
                downloadAsyn.setCancaled(true);
//                stopForeground(true);
            }
        }
    }

}
