package com.systeminfos.jvm.thread.exit;

import lombok.SneakyThrows;

public class InterruptTest extends Thread {

    public void run() {
        super.run();
        for (int i = 0; i < 200000; i++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("isInterrupted i=" + i);
                break;
            }
            System.out.println("i=" + i);
        }
    }


    /**
     * 标志位：使用 Interrupted() 判断线程是否被中断，
     * param args
     *
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        InterruptTest exit = new InterruptTest();
        exit.start();
        Thread.sleep(200);
        exit.interrupt();
    }
}
