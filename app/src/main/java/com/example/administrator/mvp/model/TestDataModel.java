package com.example.administrator.mvp.model;


import android.util.Log;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestDataModel extends BaseModel<String> {
    @Override
    public void execute(Callback<String> callback) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                OkHttpClient okHttpClient=new OkHttpClient();
                Request request=new Request.Builder()
                        .get()
                        .url(mParams[0])
                        .build();
                Call call=okHttpClient.newCall(request);
                //同步调用,返回Response,会抛出IO异常
                Response response=call.execute();
                if (response.code()==200){
                    String body=response.body().string();
                    emitter.onNext(body);
                }else {
                    emitter.onNext("error");
                }
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (s.equals("error"))
                            callback.onError();
                        else
                            callback.onSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        callback.onComplete();
                    }
                });


    }
}
