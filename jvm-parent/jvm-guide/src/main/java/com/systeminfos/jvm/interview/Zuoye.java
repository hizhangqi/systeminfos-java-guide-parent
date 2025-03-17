package com.systeminfos.jvm.interview;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @基本功能:
 * @program:LinkedMan2
 * @author: 闫哥
 * @create:2024-01-11 10:25:14
 **/
public class Zuoye {
    /**
     * 从控制台输入一个字符串，记录出现次数最多的字符，并输出这个字符出现了多少次。
     * 例如:
     * 从控制台输入 hello，输出: 出现次数最多的字符是 'l'，出现了2次。
     * 从控制台输入 helle，输出: 出现次数最多是字符出 'l'和'e'，出现了2次
     */
    public static void main(String[] args) {
        //Map<char[], Long> charMap = Arrays.asList("".toCharArray).stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        /**
         *  思路：
         *    控制台输入一个字符串，将该字符串转换为char数组
         *    定义一个Map集合，Map的Key 是字符，value 出现的次数
         *    遍历map的values 获取最大值，遍历所有的key,看哪个key 的value 等于最大值，打印出来
         */
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入一个字符串：");
        // next 和nextLine 区别
        String word = scanner.nextLine();
        char[] array = word.toCharArray();
        // HashMap默认长度多少？每次扩容，扩多少？ 底层使用的是什么数据结构？
        // 八大基本数据类型 各占多少字节
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for (char c : array) {
            if (hashMap.containsKey(c)) {
                hashMap.put(c, hashMap.get(c) + 1);
            } else {
                hashMap.put(c, 1);
            }
        }
        // hello
        // h  1
        // e  1
        // l  2
        // o  1
        Collection<Integer> values = hashMap.values();
        Integer max = Collections.max(values);// 2
        Set<Map.Entry<Character, Integer>> entries = hashMap.entrySet();
        for (Map.Entry<Character, Integer> entry : entries) {
            if (Objects.equals(entry.getValue(), max)) {
                System.out.println("最大值是" + max + "，出现次数最多的是" + entry.getKey());
            }
        }
    }

}