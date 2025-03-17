package com.systeminfos.shardingJdbc5.controller;

import com.systeminfos.shardingJdbc5.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService service;

    @GetMapping("/createUserInfo")
    public String createUserInfo() {
        service.createUserInfo(1, "1");
        return "ok";
    }

}
