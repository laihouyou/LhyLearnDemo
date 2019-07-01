package com.example.administrator.mvp.presenter;

import com.example.administrator.mvp.view.BaseView;

public class BasePresenter<V extends BaseView> {
    private V mvpView;

    /**
     * 与view进行绑定
     * @param mvpView
     */
    public void attachView(V mvpView){
        this.mvpView=mvpView;
    }

    /**
     * 与view进行解绑
     */
    public void detachView(){
        this.mvpView=null;
    }

    /**
     * 与view进行解绑
     */
    public boolean isViewAttach(){
        return this.mvpView!=null;
    }

    /**
     * 获取绑定的view
     * @return
     */
    public V getMvpView(){
        return mvpView;
    }
}
