package com.systeminfos.jvm.thread.exit;

import lombok.SneakyThrows;

public class ExitFlagTest {

    private volatile boolean exitFlag = false;

    @SneakyThrows
    public void run() {
        while (!exitFlag) {
            Thread.sleep(1000);
        }
    }

    public void stop() {
        exitFlag = true;
    }

    /**
     * 标志位：使用 volatile 关键字修饰 exitFlag 变量，确保线程能够及时感知到 exitFlag 的变化。
     * param args
     *
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ExitFlagTest exit = new ExitFlagTest();
        Thread thread = new Thread(exit::run);
        thread.start();
        Thread.sleep(5000);
        exit.stop();
    }
}
