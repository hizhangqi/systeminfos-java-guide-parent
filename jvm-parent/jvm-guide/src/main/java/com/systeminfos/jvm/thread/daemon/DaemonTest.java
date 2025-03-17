package com.systeminfos.jvm.thread.daemon;

/**
 * 守护线程是一种特殊的线程，在后台默默地完成一些系统性的服务，比如垃圾回收线程、JIT线程都是守护线程。
 * 与之对应的是用户线程，用户线程可以理解为是系统的工作线程，它会完成这个程序需要完成的业务操作。
 * 如果用户线程全部结束了，意味着程序需要完成的业务操作已经结束了，系统可以退出了。
 * 所以当系统只剩下守护进程的时候，java虚拟机会自动退出
 */
public class DaemonTest {
    public static void main(String[] args) {
        daemonTest();
//        unDaemonTest();
    }

    /**
     * 其实在main线程运行结束后，JVM会启动一个DestroyJavaVM的线程，该线程会等待所有的用户线程结束后终止JVM
     */
    private static void daemonTest() {
        //创建并启动线程
        Thread child = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(111);
                for (; ; ) {
                }
            }
        });
        //设置成为守护线程
        child.setDaemon(true);
        child.start();
        System.out.println("mainThread is over");
    }

    /**
     * 父线程结束后，子线程还是可以继续存在的并运行的，子线程的生命周期并不会受父线程影响。这也就说明了在用户进程还存在的情况下，JVM不会退出
     */
    private static void unDaemonTest() {
        //创建并启动线程
        Thread child = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(111);
                for (; ; ) {
                }
            }
        });
        child.start();
        System.out.println("mainThread is over");
    }

}
