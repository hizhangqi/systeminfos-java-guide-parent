package com.systeminfos.jvm.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringTest {

    /**
     * 测试堆栈跟踪信息
     * javap -c  StringTest.java -> StringTest.txt
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        String c = new String("hello");
//        Thread.dumpStack(); // 打印当前线程的堆栈跟踪信息

//        while (true) {
//            log.info(c);
//            Thread.sleep(5000);
//        }
    }
}
