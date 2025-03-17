package com.systeminfos.springboot3demo.jdk21;

/**
 * •	虚拟线程（又称轻量线程）正式成为生产环境中的功能，是 Project Loom 的核心成果。
 * •	虚拟线程相比传统线程更轻量级，可以更高效地处理大规模并发任务。
 * •	大幅简化了编写高并发程序的复杂性，不需要使用回调或异步框架。
 * 优势：
 * •	高并发：虚拟线程的创建成本非常低，可以轻松处理数百万级别的线程。
 * •	与现有 API 完全兼容。
 */
public class VirtualThreadExample {
    public static void main(String[] args) throws InterruptedException {
        Thread.startVirtualThread(() -> {
            System.out.println("Running in a virtual thread!");
        });
        // 让主线程等待一会儿
        Thread.sleep(1000);

        Thread thread = Thread.startVirtualThread(() -> {
            System.out.println("Running in a virtual thread2!");
        });
        thread.join(); // 等待虚拟线程完成
    }
}