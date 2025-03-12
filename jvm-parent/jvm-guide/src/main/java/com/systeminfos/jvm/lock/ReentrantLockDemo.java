package com.systeminfos.jvm.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 注意事项：
 * 锁的公平性：ReentrantLock 可以选择是否为公平锁。默认是非公平锁，可以通过构造函数 new ReentrantLock(true) 指定为公平锁。
 * 可重入性：同一个线程可以多次获取同一个锁，而不会导致死锁。
 * 性能：在大多数情况下，ReentrantLock 的性能与 synchronized 相当，但在某些特定场景下（如锁的竞争非常激烈）可能会有更好的性能。
 */
public class ReentrantLockDemo {
    private final ReentrantLock lock = new ReentrantLock();

    public void someMethod() {
        lock.lock(); // 获取锁
        try {
            // 执行临界区代码
            System.out.println("执行临界区代码");
        } finally {
            lock.unlock(); // 释放锁
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo demo = new ReentrantLockDemo();

        // 创建多个线程来测试锁
        Thread t1 = new Thread(() -> demo.someMethod());
        Thread t2 = new Thread(() -> demo.someMethod());

        t1.start();
        t2.start();
    }
}
