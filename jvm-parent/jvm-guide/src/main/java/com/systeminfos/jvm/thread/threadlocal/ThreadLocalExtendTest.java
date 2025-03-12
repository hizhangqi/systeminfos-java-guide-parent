package com.systeminfos.jvm.thread.threadlocal;

/**
 * ThreadLocal在父线程中被设置后，在子线程里面是获取不到，因为这是两个不同的线程，
 * 只有父线程口袋里装了东西，子线程却并没有继承父线程的口袋，所以ThreadLocal并没有继承性。
 */
public class ThreadLocalExtendTest {
    //1.创建ThreadLocal变量 为Sring类型
    public static ThreadLocal<String> localVariable = new ThreadLocal<>();

    public static void main(String[] args) {
        //在父线程的口袋里面加入HelloWorld
        localVariable.set("Hello World");
        //创建并启动子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //输出子线程口袋里面的值
                System.out.println("child:" + localVariable.get());
            }
        }).start();

        //输出父线程口袋里面的值
        System.out.println("main:" + localVariable.get());
    }
}