package com.example.coroutines.rxjava;

import android.content.Context;

import com.example.coroutines.rxjava.net.BaseScheduler;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：余天然 on 2017/6/14 下午6:43
 */
public class BaseTransformer<T> extends Observable.Transformer<T, T> {

    private LifecycleTransformer<T> transformer;
    private LoadingDialog dialog;

    /**
     * @param rx      方便绑定生命周期
     * @param context 决定是否网络进度条，为null则无
     */
    public BaseTransformer(LifecycleProvider<?> rx, Context context) {
        this.transformer = rx.bindToLifecycle();
        if (context != null) {
            this.dialog = new LoadingDialog(context);
        }
    }

    public BaseTransformer(LifecycleProvider<?> rx, Context context, boolean isShowLoading) {
        this.transformer = rx.bindToLifecycle();
        if (context != null && isShowLoading == true) {
            this.dialog = new LoadingDialog(context);
        }
    }

    /**
     * 统一加上进度条、线程调度等
     */
    @Override
    public Observable<T> call(Observable<T> observable) {
        return observable
                .throttleFirst(1, TimeUnit.SECONDS)//取1秒之内的最后一次,防止重复提交
                .subscribeOn(BaseScheduler.getScheduler())//在异步线程执行耗时操作，对上面的操作有用
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (dialog != null && !dialog.isShowing()) {
                            dialog.show();
                        }
                    }
                })
                .subscribeOn(Schedulers.mainThread())//在主线程显示进度条，对上面的操作有用
                .compose(transformer)//绑定生命周期
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call(Throwable throwable) {
                        if (dialog != null) {
                            dialog.cancel();
                        }
                        return Observable.error(new UnknownException(throwable));
                    }
                })//公共错误拦截
                .map(new Func1<T, T>() {
                    @Override
                    public T call(T t) {
                        if (dialog != null) {
                            dialog.cancel();
                        }
                        return t;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());//在主线程回调，对下面的操作有用
    }


}
