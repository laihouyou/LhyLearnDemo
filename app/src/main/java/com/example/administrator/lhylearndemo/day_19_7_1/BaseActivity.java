package com.example.administrator.lhylearndemo.day_19_7_1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.administrator.mvp.view.BaseView;

public class BaseActivity extends AppCompatActivity implements BaseView {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }

    @Override
    public void showLoading() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void showToats(String msg) {
        if (Looper.myLooper()==Looper.getMainLooper())
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorToats() {
        if (Looper.myLooper()==Looper.getMainLooper())
            Toast.makeText(this,"出错了",Toast.LENGTH_LONG).show();

    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }
}
