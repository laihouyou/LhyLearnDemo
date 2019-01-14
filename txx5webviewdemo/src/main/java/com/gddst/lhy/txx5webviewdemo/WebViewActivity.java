package com.gddst.lhy.txx5webviewdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gddst.lhy.txx5webviewdemo.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class WebViewActivity extends AppCompatActivity {
    private WebView x5WebView;
    private ProgressBar progressBar;
    private String url="";
    private File txtFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initPerment();
    }

    private void initPerment() {
//        RxPermissionsUtil.requestEachRxPermission(this,
//                Manifest.permission.INTERNET
//        );

        // 在要调用权限的activity中插入该方法。可以写到onCreate()中。
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 判断是否有这个权限，是返回PackageManager.PERMISSION_GRANTED，否则是PERMISSION_DENIED
            // 这里我们要给应用授权所以是!= PackageManager.PERMISSION_GRANTED
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // 如果应用之前请求过此权限但用户拒绝了请求,且没有选择"不再提醒"选项 (后显示对话框解释为啥要这个权限)，此方法将返回 true。
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {


                } else {

                    // requestPermissions以标准对话框形式请求权限。123是识别码（任意设置的整型），用来识别权限。应用无法配置或更改此对话框。
                    //当应用请求权限时，系统将向用户显示一个对话框。当用户响应时，系统将调用应用的 onRequestPermissionsResult() 方法，向其传递用户响应。您的应用必须替换该方法，以了解是否已获得相应权限。回调会将您传递的相同请求代码传递给 requestPermissions()。
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            123);

                }
            }else {
                try {
                    initRul();
                    initview();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }else {
            try {
                initRul();
                initview();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void initRul() throws IOException {
        String directoy=Environment.getExternalStorageDirectory().getAbsolutePath();
        String path=directoy+"/"+getString(R.string.app_name);
        txtFile=new File(path+"/url.txt");

        int starts= FileUtils.CreateFile(txtFile.getPath());
        if (starts==1){
            FileUtils.writeStr(txtFile,"https://cn.bing.com");
        }
        if (txtFile.isFile()){
            InputStream inputStream=new FileInputStream(txtFile);
            if (inputStream!=null){
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                String line;
                while ((line=bufferedReader.readLine())!=null){
                    url+=line;
                }
                inputStream.close();
            }
        }
    }

    private void initview() {
        x5WebView=findViewById(R.id.x5WebView);
        progressBar=findViewById(R.id.progressBar);

        x5WebView.getSettings().setLightTouchEnabled(true);
        x5WebView.getSettings().setJavaScriptEnabled(true);//设置webview打开的页面里面的js生效
        x5WebView.setScrollbarFadingEnabled(true);
        x5WebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        x5WebView.getSettings().setDefaultTextEncodingName("UTF-8");
        x5WebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

        x5WebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

//            @Override
//            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
//                sslErrorHandler.proceed();  // 接受所有网站的证书
//                super.onReceivedSslError(webView, sslErrorHandler, sslError);
//            }

            @Override
            public void onPageFinished(final WebView webView, String s) {
                super.onPageFinished(webView, s);

            }
        });

        x5WebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(i);
                if (i==100){


                    if (progressBar.isShown()){
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });

        if (!TextUtils.isEmpty(url)&&
                (Patterns.WEB_URL.matcher(url).matches() || URLUtil.isValidUrl(url))){
            x5WebView.loadUrl(url);
//            Toast.makeText(this,
//                    "如需修改url，请打开SD卡根目录下的"
//                            +getString(R.string.app_name)+
//                            "目录，打开url.txt文本，写入正确的url地址",
//                    Toast.LENGTH_LONG).show();
        }else {
            AlertDialog alertDialog=new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("获取url失败，请打开SD卡根目录下的"+getString(R.string.app_name)+"目录，打开url.txt文本，写入正确的url地址")
                    .create();
            alertDialog.show();

            progressBar.setVisibility(View.GONE);
        }

    }

    //当应用请求权限时，系统将向用户显示一个对话框。当用户响应时，系统将调用应用的 onRequestPermissionsResult() 方法，向其传递用户响应。您的应用必须替换该方法，以了解是否已获得相应权限。回调会将您传递的相同请求代码传递给 requestPermissions()。
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 123: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //这里的两句语句是我写的创建文件的语句，在授权成功是时候会调用这里的语句。
                    try {
                        initRul();
                        initview();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    Toast.makeText(this, "文件目录已经创建好了", Toast.LENGTH_SHORT).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * 使点击回退按钮不会直接退出整个应用程序而是返回上一个页面
     *
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK&&x5WebView.canGoBack()){
            x5WebView.goBack();//返回上个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);//退出整个应用程序
    }
}
