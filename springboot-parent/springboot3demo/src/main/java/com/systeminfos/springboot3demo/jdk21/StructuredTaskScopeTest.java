package com.systeminfos.springboot3demo.jdk21;

import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeUnit;

public class StructuredTaskScopeTest {
    public static void main(String[] args) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            // 创建并发任务
            StructuredTaskScope.Subtask<String> task1 = scope.fork(FetchDataExample::fetchDataFromService1);
            StructuredTaskScope.Subtask<String> task2 = scope.fork(FetchDataExample::fetchDataFromService2);

            // 等待所有任务完成
            scope.join();
            scope.throwIfFailed();

            // 输出任务结果
            System.out.println("Result from Service 1: " + task1.get());
            System.out.println("Result from Service 2: " + task2.get());
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }

}

class FetchDataExample {
    public static String fetchDataFromService1() throws InterruptedException {
        // 模拟一个耗时的网络请求
        TimeUnit.SECONDS.sleep(2); // 模拟延迟
        return "Data from Service 1";
    }

    public static String fetchDataFromService2() throws InterruptedException {
        // 模拟另一个耗时的网络请求
        TimeUnit.SECONDS.sleep(3); // 模拟延迟
        return "Data from Service 2";
    }
}