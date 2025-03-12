package com.systeminfos.shardingJdbc31.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ThreadLocalDemo {

    private ThreadLocal<String> userSession = new ThreadLocal<>();

    @GetMapping("/getUser")
    public String getUser(@RequestParam("userId") String userId) throws InterruptedException {
        try {
            log.info("threadLocal:{}", userSession.get());
            Thread.sleep(2000);
            userSession.set(userId);

            String response = "Processing request for user: " + userSession.get();
            return response;
        } finally {
            //userSession.remove();
        }
    }


}
