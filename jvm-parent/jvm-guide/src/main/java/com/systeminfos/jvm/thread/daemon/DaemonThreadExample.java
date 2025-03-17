package com.systeminfos.jvm.thread.daemon;

/**
 * Java 守护线程是一种特殊类型的线程，它主要被用作垃圾回收器（GC）等系统维护任务。当所有非守护线程结束时，守护线程也会被自动终止。
 * 使用场景：
 * 后台任务：守护线程常用于在后台执行一些系统维护任务，如垃圾回收、日志记录等。
 * 任务分发：可以使用守护线程来处理一些任务分发和管理工作，确保任务能够及时执行。
 */
public class DaemonThreadExample {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(new DaemonTask(), "DaemonThread");
        daemonThread.setDaemon(true); // 设置为守护线程
        daemonThread.start();
        System.out.println(daemonThread.getName() + " is daemon: " + daemonThread.isDaemon());
    }

    static class DaemonTask implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    // 执行任务
                    System.out.println("Daemon thread is running...");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                // 处理中断异常
                Thread.currentThread().interrupt();
            }
        }
    }
}
