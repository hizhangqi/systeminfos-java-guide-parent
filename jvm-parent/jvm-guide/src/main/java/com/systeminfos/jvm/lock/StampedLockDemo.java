package com.systeminfos.jvm.lock;

import java.util.concurrent.locks.StampedLock;

/**
 * 当运行上述代码时，多个读线程可以同时读取数据，但写线程会独占资源进行写操作。读线程首先尝试乐观读锁，如果数据未被修改，则直接返回结果；否则，升级为悲观读锁，确保读取的数据一致性。
 * 注意事项
 * 乐观读锁：乐观读锁假设数据在读取过程中不会被修改，如果数据被修改，则需要升级为悲观读锁。
 * 性能：在读多写少的场景中，StampedLock 可以显著提高并发性能。
 * 锁的公平性：StampedLock 不支持公平锁。
 */
public class StampedLockDemo {
    private final StampedLock stampedLock = new StampedLock();
    private double x, y;

    /**
     * 移动对象的位置
     * 此方法使用了乐观锁机制来更新对象的坐标，确保在并发环境下线程安全
     *
     * @param deltaX X轴上的增量
     * @param deltaY Y轴上的增量
     */
    public void move(double deltaX, double deltaY) {
        // 获取写锁，因为即将修改坐标值
        long stamp = stampedLock.writeLock();
        try {
            // 更新坐标
            x += deltaX;
            y += deltaY;
            // 打印当前线程名称和更新后的坐标，用于调试和日志记录
            System.out.println(Thread.currentThread().getName() + " 写入数据: (" + x + ", " + y + ")");
        } finally {
            // 释放写锁，确保其他线程可以获取锁
            stampedLock.unlockWrite(stamp);
        }
    }

    /**
     * 计算当前点到原点(0,0)的距离
     * 该方法使用了乐观读锁和悲观读锁来处理并发情况，以保证数据的一致性和提高程序的并发性能
     *
     * @return 当前点到原点的距离
     */
    public double distanceFromOrigin() {
        long stamp = stampedLock.tryOptimisticRead(); // 尝试获取乐观读锁
        double currentX = x, currentY = y;
        if (!stampedLock.validate(stamp)) { // 检查乐观读锁是否仍然有效
            stamp = stampedLock.readLock(); // 升级为悲观读锁
            try {
                currentX = x;
                currentY = y;
            } finally {
                stampedLock.unlockRead(stamp); // 释放悲观读锁
            }
        }
        // 计算并返回到原点的距离
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    public static void main(String[] args) {
        StampedLockDemo demo = new StampedLockDemo();

        // 创建多个读线程和写线程来测试锁
        Thread reader1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " 计算距离: " + demo.distanceFromOrigin());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Reader1");

        Thread reader2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " 计算距离: " + demo.distanceFromOrigin());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Reader2");

        Thread writer1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                demo.move(1.0, 1.0);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Writer1");

        reader1.start();
        reader2.start();
        writer1.start();
    }
}
