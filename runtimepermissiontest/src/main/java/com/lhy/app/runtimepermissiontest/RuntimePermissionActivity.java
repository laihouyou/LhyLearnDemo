package com.lhy.app.runtimepermissiontest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class RuntimePermissionActivity extends AppCompatActivity {
    private String callphoneStr;
    private String phoneStrate="tel:";
    AppCompatEditText appCompatEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permission);
        initView();
    }

    private void initView() {
        appCompatEditText=findViewById(R.id.appCompatEditText);
        AppCompatButton appCompatButton=findViewById(R.id.button_call);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        RuntimePermissionActivity.this,
                        Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
                    callphoneStr=appCompatEditText.getText().toString();
                    CallPhone(phoneStrate+callphoneStr);
                }else {
                    ActivityCompat.requestPermissions(
                            RuntimePermissionActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE} ,
                            100);
                }
            }
        });
    }

    public void CallPhone(String callphoneStr){
        Intent intent=new Intent(Intent.ACTION_CALL);  //拨打电话
        intent.setData(Uri.parse(callphoneStr));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                callphoneStr=appCompatEditText.getText().toString();
                CallPhone(phoneStrate+callphoneStr);
            }else {
                Toast.makeText(this,getString(R.string.ed_hint3),Toast.LENGTH_LONG).show();
            }
        }
    }
}
