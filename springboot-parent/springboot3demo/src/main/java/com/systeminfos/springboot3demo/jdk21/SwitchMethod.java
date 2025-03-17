package com.systeminfos.springboot3demo.jdk21;

/**
 * 模式匹配 for switch（Pattern Matching for switch）[JEP 441]
 * <p>
 * •	让 switch 表达式更加灵活，支持复杂的模式匹配。
 * •	扩展了对 switch 中类型检查和条件分支的支持，使得代码更加简洁直观。
 */
public class SwitchMethod {
    static void testPatternMatching(Object obj) {
        switch (obj) {
            case String s when s.length() > 5 -> System.out.println("Long String: " + s);
            case Integer i when i > 10 -> System.out.println("Large Integer: " + i);
            case null -> System.out.println("Object is null");
            default -> System.out.println("Unknown type");
        }
    }

    public static void main(String[] args) {
        testPatternMatching("Hello, World!");
        testPatternMatching(123);
    }

}
