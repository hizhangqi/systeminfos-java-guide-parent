package com.systeminfos.jvm.thread;

/**
 * 通过实现 Runnable 接口，实现多线程
 */
public class WorkRunnable implements Runnable {

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
        Thread t = new Thread(new WorkRunnable());
        t.start();
    }

}
