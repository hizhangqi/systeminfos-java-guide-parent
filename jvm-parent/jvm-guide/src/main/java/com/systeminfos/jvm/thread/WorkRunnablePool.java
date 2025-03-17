package com.systeminfos.jvm.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通过线程池，实现多线程，不太适合并发，因为线程池是固定线程池，线程池中的线程是固定的，如果线程池中的线程都在执行任务，那么新的任务就会等待线程池中的线程执行完任务，然后再执行任务。
 */
public class WorkRunnablePool implements Runnable {

    @Override
    public void run() {
        System.out.println("work thread start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("work thread end");
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(new WorkRunnablePool());
    }

}
