package com.example.administrator.mvp.view;

import android.widget.ImageButton;

public interface TestPicView extends BaseView{
    /**
     * 加载图片
     * @param imageButton
     * @param url
     */
    void showPic(String url);
    /**
     * 隐藏图片
     * @param imageButton
     * @param url
     */
    void hidePic();
}
