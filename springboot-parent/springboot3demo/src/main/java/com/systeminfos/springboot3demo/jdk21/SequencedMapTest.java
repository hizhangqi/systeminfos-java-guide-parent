package com.systeminfos.springboot3demo.jdk21;

import java.util.LinkedHashMap;
import java.util.SequencedMap;

/**
 * •	新增了 SequencedCollection、SequencedSet 和 SequencedMap 接口，用于表示有序集合。
 * •	可以更轻松地从头或尾访问集合中的元素。
 */
public class SequencedMapTest {
    public static void main(String[] args) {
        SequencedMap<String, Integer> map = new LinkedHashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        System.out.println(map.firstEntry());  // 获取第一个元素
        System.out.println(map.lastEntry());   // 获取最后一个元素
    }
}
