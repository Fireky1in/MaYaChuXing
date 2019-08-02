package com.ipd.mayachuxing.progress;

import com.ipd.mayachuxing.utils.ExceptionHandle;

/**
 * 请求监听
 */

public interface ObserverResponseListener<T> {
    /**
     * 响应成功
     * @param t
     */
    void onNext(T t);

    /**
     * 响应失败
     * @param e
     */
    void onError(ExceptionHandle.ResponeThrowable e);
}
