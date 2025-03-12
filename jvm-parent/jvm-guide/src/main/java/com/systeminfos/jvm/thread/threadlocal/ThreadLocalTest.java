package com.systeminfos.jvm.thread.threadlocal;

public class ThreadLocalTest {

    //1.创建ThreadLocal变量 为Sring类型
    public static ThreadLocal<String> localVariable = new ThreadLocal<>();

    //2.创建一个打印ThreadLocal的方法
    public static void printThreadLcoal(String str) {
        //打印当前线程的ThreadLocal变量的值
        System.out.println(str + ":" + localVariable.get());
        //清除ThreadLocal的值
        localVariable.remove();
    }

    public static void main(String[] args) {
        //1.创建线程One
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                //设置One线程的ThreadLocal值
                localVariable.set("ThreadOne local variable");
                //将One线程的ThreadLocal值打印出来
                printThreadLcoal("threadOne");
                //打印清除之后的ThreadLocal的值
                System.out.println("ThreadOne remove after" + ":" + localVariable.get());
            }
        });

        //1.创建线程Two
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                //设置Two线程的ThreadLocal值
                localVariable.set("ThreadTwo local variable");
                //将Two线程的ThreadLocal值打印出来
                printThreadLcoal("threadTwo");
                //打印清除之后的ThreadLocal的值
                System.out.println("ThreadTwo remove after" + ":" + localVariable.get());
            }
        });

        //启动线程
        threadOne.start();
        threadTwo.start();
    }

}