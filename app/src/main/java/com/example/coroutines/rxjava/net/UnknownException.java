package com.example.coroutines.rxjava.net;

/**
 * 未知异常
 * <p>
 * 作者：余天然 on 2017/6/13 上午10:54
 */
public class UnknownException extends RuntimeException {

    public UnknownException(String message) {
        super(message);
    }

    public UnknownException(Throwable cause) {
        super("网络不给力", cause);
    }

    public UnknownException(String message, Throwable throwable) {
        super(message, throwable);
    }
}