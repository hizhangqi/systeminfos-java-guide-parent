package com.systeminfos.jvm.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

/**
 * 注意事项：
 * 锁的公平性：ReentrantReadWriteLock 也可以选择是否为公平锁。默认是非公平锁，可以通过构造函数 new ReentrantReadWriteLock(true) 指定为公平锁。
 * 性能：在读多写少的场景中，ReentrantReadWriteLock 可以显著提高并发性能。
 */
public class ReentrantReadWriteLockDemo {
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();
    private int value;

    public void read() {
        readLock.lock(); // 获取读锁
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取数据: " + value);
        } finally {
            readLock.unlock(); // 释放读锁
        }
    }

    public void write(int newValue) {
        writeLock.lock(); // 获取写锁
        try {
            value = newValue;
            System.out.println(Thread.currentThread().getName() + " 写入数据: " + value);
        } finally {
            writeLock.unlock(); // 释放写锁
        }
    }

    public static void main(String[] args) {
        ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo();

        // 创建多个读线程和写线程来测试锁
        Thread reader1 = new Thread(() -> demo.read(), "Reader1");
        Thread reader2 = new Thread(() -> demo.read(), "Reader2");
        Thread writer1 = new Thread(() -> demo.write(10), "Writer1");
        Thread writer2 = new Thread(() -> demo.write(20), "Writer2");

        reader1.start();
        reader2.start();
        writer1.start();
        writer2.start();
    }
}
