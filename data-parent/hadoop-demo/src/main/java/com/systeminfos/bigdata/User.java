package com.systeminfos.bigdata;

import java.io.Serializable;

/**
 * @Author laoyan
 * @Description TODO
 * @Date 2022/8/1 11:43
 * @Version 1.0
 */
public class User implements Serializable {

    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
