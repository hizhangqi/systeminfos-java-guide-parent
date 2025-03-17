package com.systeminfos.jvm.thread.exit;

import lombok.SneakyThrows;

public class StopMethodTest extends Thread {

    @SneakyThrows
    public void run() {
        while (true) {
            Thread.sleep(1000);
        }
    }

    /**
     * 标志位：使用  stop() 方法退出线程
     * param args
     *
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        StopMethodTest exit = new StopMethodTest();
        exit.start();
        Thread.sleep(5000);
        exit.stop();
    }
}
