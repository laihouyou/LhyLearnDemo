package com.example.administrator.lhylearndemo.day6_downtest;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import com.example.administrator.lhylearndemo.R;
import com.example.administrator.lhylearndemo.utils.RxPermissionsUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;

public class DwonloadActivity extends AppCompatActivity  implements View.OnClickListener {
    private AppCompatTextView progressText;
    private ProgressBar progressBar;

    private DwonloadService.DwonloadBudle budle;
    private MyHandler myHandler;

    private static final String url="http://mapopen-pub-androidsdk.cdn.bcebos.com/map/sample/BaiduLBS_AndroidSDK_Sample.zip";
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

    private boolean isShowRequestPermissionRationale(String permission){
        return ActivityCompat.shouldShowRequestPermissionRationale(this,permission);
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
        myHandler=new MyHandler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startDwonload:
                budle.startDwonload(url,myHandler);
                break;
            case R.id.pausedDwonload:
                budle.pausedDwonload();
                break;
            case R.id.canceledDwonload:
                budle.canceledDwonload();
                break;
        }
    }

    private static class MyHandler extends Handler{

        private final WeakReference<DwonloadActivity> mActivity;
        public MyHandler(DwonloadActivity activity){
            this.mActivity=new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DwonloadActivity activity=mActivity.get();
            if (activity!=null){
               switch (msg.what){
                   case 1:
                       Bundle bundle=msg.getData();
                       if (bundle!=null){
                           int progess=bundle.getInt("progess");
                           activity.progressText.setText(progess+"%");
                           activity.progressBar.setProgress(progess);
                       }
                       break;
               }
            }
        }

    }

    private void initPamean() {
        List<String> permissionList=new ArrayList<>();
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.INTERNET);

        RxPermissionsUtil.requestEachRxPermission(this,new String[]{Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode==10){
//            if (grantResults.length>0){
//                for (int i = 0; i < grantResults.length; i++) {
//                    if (grantResults[i]==PackageManager.PERMISSION_GRANTED){
//                        Toast.makeText(this,getString(R.string.msg8),Toast.LENGTH_LONG).show();
//                    }else {
//                        Toast.makeText(this,getString(R.string.msg9),Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        }
//    }

    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            budle= (DwonloadService.DwonloadBudle) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    } ;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
