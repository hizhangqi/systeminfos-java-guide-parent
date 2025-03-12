package com.systeminfos.springboot3demo.grammar.cycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 解决办法：
 * 1：使用setter方法注入
 * 3: 使用构造函数注入+@Lazy注解
 * 2：循环依赖的类加上@Lazy注解，并且循环依赖的类中，使用@Autowired注解
 */
@Service
public class Louzai2 {

    @Lazy
    @Autowired
    private Louzai1 louzai1;

    /*public Louzai2(@Lazy Louzai1 louzai1) {
        this.louzai1 = louzai1;
    }*/

    /*public Louzai1 getLouzai1() {
        return louzai1;
    }

    public void setLouzai1(Louzai1 louzai1) {
        this.louzai1 = louzai1;
    }*/

    public void test2() {
        System.out.println("test2");
    }
}
