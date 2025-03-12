package com.systeminfos.jvm.thread;

/**
 * 通过继承Thread的方式创建线程
 */
public class WorkThread extends Thread {

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
        WorkThread workRunnable = new WorkThread();
        workRunnable.start();

        // 通过lambda创建线程
        Thread thread = new Thread(() -> System.out.println("hello world"));
        thread.start();
        //Exception in thread "main" Exception in thread "main" java.lang.IllegalThreadStateException
        //thread.start();
    }

}
