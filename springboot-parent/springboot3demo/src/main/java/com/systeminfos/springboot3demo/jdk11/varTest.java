package com.systeminfos.springboot3demo.jdk11;

import java.util.Optional;
import java.util.function.BiFunction;

/**
 * cd /Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home/bin
 * ./java /Users/zhangqi/Documents/Workspaces/systeminfos/systeminfos-java-parent/springboot-parent/springboot3demo/src/main/java/com/systeminfos/springboot3demo/jdk11/varTest.java
 */
public class varTest {

    public static void main(String[] args) {

        //显式指定目标类型
        BiFunction<Integer, Integer, Integer> lambda = (var x, var y) -> x * y;
        System.out.println(lambda.apply(2, 3)); // 输出 6

        //去掉 var（使用类型推断）
        BiFunction<Integer, Integer, Integer> lambda2 = (x, y) -> x + y;
        System.out.println(lambda2.apply(2, 3)); // 输出 5

        //引用方法
        BiFunction<Integer, Integer, Integer> lambda3 = Integer::sum;
        System.out.println(lambda3.apply(2, 3)); // 输出 5

        String str = "  Hello World  ";
        System.out.println(str.isBlank());        // false
        System.out.println(str.strip());          // "Hello World"
        System.out.println(str.stripLeading());   // "Hello World  "
        System.out.println(str.stripTrailing());  // "  Hello World"
        System.out.println("Hi".repeat(3));       // "HiHiHi"

        Optional<String> optional = Optional.ofNullable(null);
        System.out.println(optional.isEmpty()); // true
        System.out.println(optional.isPresent()); // false
        optional.orElseThrow(); // 抛出 NoSuchElementException
    }

}
