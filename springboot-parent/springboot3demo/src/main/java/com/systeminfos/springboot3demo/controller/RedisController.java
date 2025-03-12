package com.systeminfos.springboot3demo.controller;

import com.systeminfos.springboot3demo.service.RedisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    // 设置 Key-Value 到 Redis
    @PostMapping("/set")
    public String set(@RequestParam(name = "key") String key, @RequestParam(name = "value") String value) {
        redisService.set(key, value, 111160); // 设置 60 秒过期
        return "Key set successfully!";
    }

    // 获取 Key 的值
    @GetMapping("/get")
    public Object get(@RequestParam(name = "key") String key) {
        return redisService.get(key);
    }

    // 删除 Key
    @DeleteMapping("/delete")
    public String delete(@RequestParam(name = "key") String key) {
        boolean result = redisService.delete(key);
        return result ? "Key deleted successfully!" : "Key not found!";
    }
}