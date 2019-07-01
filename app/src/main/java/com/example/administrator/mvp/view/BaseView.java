package com.example.administrator.mvp.view;

import android.content.Context;

public interface BaseView {
    /**
     * 显示需要加载的view
     */
    void showLoading();
    /**
     * 隐藏需要加载的view
     */
    void hideLoading();
    /**
     * 显示提示
     */
    void showToats(String msg);
    /**
     * 显示提示
     */
    void showErrorToats();
    /**
     * 获取上下文
     */
    Context getContext();
}
