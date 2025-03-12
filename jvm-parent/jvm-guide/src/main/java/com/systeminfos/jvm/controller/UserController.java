package com.systeminfos.jvm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.yuque.com/renyong-jmovm/dadudu/cp8g6ke6zyhy2esd
 *
 * @author ：zhangqi
 */
@RestController
public class UserController {

    private List<byte[]> list = new ArrayList<>();

    /**
     * 测试内存溢出
     * java -jar -Xmx500m -Xms500m jvm-guide.jar
     *
     * @return
     */
    @RequestMapping("/test")
    public String test() {
        list.add(new byte[1024 * 1024 * 100]); //100m
        return "success";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

}
