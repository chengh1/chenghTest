package com.chengh.db.util.threadPool;

import java.util.concurrent.Callable;

/**
 * 线程任务只返回当前线程的名称
 */
public class TestThreadTask implements Callable<String> {

    @Override
    public String call() {
        return Thread.currentThread().getName();
    }
}
