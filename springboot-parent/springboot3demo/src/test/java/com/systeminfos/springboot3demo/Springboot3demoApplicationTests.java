package com.systeminfos.springboot3demo;

import com.systeminfos.springboot3demo.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Springboot3demoApplicationTests {

    @Autowired
    private IUserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    public void insertRequiredNewTest() {
        userService.insertRequiredNew();
    }

    @Test
    public void insertRequiredTest() throws Exception {
        userService.insertRequired();
    }

}
