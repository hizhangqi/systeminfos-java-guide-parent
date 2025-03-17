package com.systeminfos.springboot3demo.jdk17;

public class NullPointTest {

    public static void main(String[] args) {
        String[] array = null;
        System.out.println(array[0]);
        //Exception in thread "main" java.lang.NullPointerException: Cannot read the array length because "array" is null
    }
}
