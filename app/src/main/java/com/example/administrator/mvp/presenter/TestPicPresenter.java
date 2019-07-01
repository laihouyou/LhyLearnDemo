package com.example.administrator.mvp.presenter;

import android.widget.ImageButton;
import android.widget.Toast;

import com.example.administrator.mvp.model.Callback;
import com.example.administrator.mvp.model.DataModel;
import com.example.administrator.mvp.view.TestPicView;

public class TestPicPresenter extends BasePresenter<TestPicView> {
    public void showPic(String token, String params){

        if (!isViewAttach()){
            return;
        }
        getMvpView().showLoading();


        DataModel
                // 设置请求标识token
                .getModel(token)
                // 添加请求参数，没有则不添加
                .params(params)
                // 注册监听回调
                .execute(new Callback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        getMvpView().showPic(data);
                    }

                    @Override
                    public void onFailure(String msg) {
                        getMvpView().showToats(msg);
                    }

                    @Override
                    public void onError() {
                        getMvpView().showErrorToats();
                    }

                    @Override
                    public void onComplete() {
                        getMvpView().hideLoading();
                    }
                });
    }

}
