package com.systeminfos.jvm.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 当运行上述代码时，生产者线程和消费者线程会交替执行，生产者生产产品后通知消费者，消费者消费产品后通知生产者。输出结果将显示生产者和消费者之间的协作过程。
 * 注意事项
 * 条件等待：condition.await() 会使当前线程等待，并释放锁。只有当其他线程调用 condition.signal() 或 condition.signalAll() 时，等待的线程才会被唤醒。
 * 条件通知：condition.signal() 会唤醒一个等待的线程，而 condition.signalAll() 会唤醒所有等待的线程。
 * 锁的使用：Condition 必须与 Lock 一起使用，确保线程安全。
 */
public class ConditionDemo {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean isProduced = false;

    public void produce() throws InterruptedException {
        lock.lock(); // 获取锁
        try {
            while (isProduced) {
                condition.await(); // 如果已经生产了，等待
            }
            // 生产产品
            System.out.println(Thread.currentThread().getName() + " 生产产品");
            isProduced = true;
            condition.signalAll(); // 通知所有等待的消费者
        } finally {
            lock.unlock(); // 释放锁
        }
    }

    public void consume() throws InterruptedException {
        lock.lock(); // 获取锁
        try {
            while (!isProduced) {
                condition.await(); // 如果没有生产，等待
            }
            // 消费产品
            System.out.println(Thread.currentThread().getName() + " 消费产品");
            isProduced = false;
            condition.signalAll(); // 通知所有等待的生产者
        } finally {
            lock.unlock(); // 释放锁
        }
    }

    public static void main(String[] args) {
        ConditionDemo demo = new ConditionDemo();

        // 创建生产者线程
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    demo.produce();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Producer");

        // 创建消费者线程
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    demo.consume();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Consumer");

        producer.start();
        consumer.start();
    }
}
