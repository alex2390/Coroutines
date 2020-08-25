package com.example.coroutines.rxjava.net;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;


/**
 * Rx线程变换器
 * <p>
 * 对发送的事件做统一处理
 * <p>
 * 作者：余天然 on 16/7/18 下午10:20
 */
public class BaseScheduler {

    //核心有2个线程，最大线程数量为20，存活时间60s
    private static Scheduler scheduler;

    public static Scheduler getScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.from(new ThreadPoolExecutor(2, 20, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>()));

        }
        return scheduler;
    }
}
