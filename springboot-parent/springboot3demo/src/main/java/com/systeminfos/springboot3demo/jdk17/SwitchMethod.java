package com.systeminfos.springboot3demo.jdk17;

public class SwitchMethod {

    static String formatObject(Object obj) {
        return switch (obj) {
            case Integer i -> "Integer: " + i;
            case String s -> "String: " + s;
            case null -> "Null value";
            default -> "Unknown type";
        };
    }

    /**
     * Null value
     * Integer: 1
     * String: Hello
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(formatObject(null));
        System.out.println(formatObject(1));
        System.out.println(formatObject("Hello"));
    }
}
