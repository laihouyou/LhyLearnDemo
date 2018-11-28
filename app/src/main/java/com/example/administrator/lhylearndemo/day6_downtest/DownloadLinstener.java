package com.example.administrator.lhylearndemo.day6_downtest;

public interface DownloadLinstener {
    //获取下载进度
    void onProgess(int progess);
    //下载成功
    void onSuccess();
    //下载失败
    void onFailed();
    //下载暂停
    void onPaused();
    //下载取消
    void onCanceled();
}
