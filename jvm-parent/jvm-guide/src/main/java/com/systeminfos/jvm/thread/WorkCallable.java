package com.systeminfos.jvm.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 通过实现Callable的方式创建线程
 */
public class WorkCallable implements Callable<String> {

    @Override
    public String call() {
        return "WorkCallable start";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new WorkCallable());
        Thread t = new Thread(futureTask);
        t.start();
        String result = futureTask.get();
        System.out.println(result);
    }

}
