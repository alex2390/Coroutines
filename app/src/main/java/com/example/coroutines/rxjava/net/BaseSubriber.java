package com.example.coroutines.rxjava.net;


import android.content.Intent;

i

import org.reactivestreams.Subscriber;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 作者：余天然 on 2017/6/8 下午2:45
 */
public abstract class BaseSubriber<T> implements Subscriber<T> {
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e);
    }

    @Override
    public void onNext(T resp) {


    }

    private void relogin() {

    }

    public void onFailure(Throwable e) {

    }

    public abstract void onSuccess(T resp);
}
