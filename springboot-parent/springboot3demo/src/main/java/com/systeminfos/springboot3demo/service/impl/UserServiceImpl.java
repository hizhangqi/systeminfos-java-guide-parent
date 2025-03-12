package com.systeminfos.springboot3demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.systeminfos.springboot3demo.entity.User;
import com.systeminfos.springboot3demo.mapper.UserMapper;
import com.systeminfos.springboot3demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangqi
 * @version 1.0
 * @desc 服务实现
 * @date 2024-12-12 11:24:24
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 批量新增重写
     *
     * @param param model集合
     * @return Boolean
     */
    @Override
    public Boolean saveBatch(List<User> param) {
        return this.getBaseMapper().saveBatch(param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void insertRequired() {
        log.info("insertRequired start");
        this.insertRequiredNew();
        User user = new User();
        user.setName("insertRequired");
        user.setUsername("insertRequired");
        user.setPassword("123456");
        user.setStatus("1");
        int i = 1 / 0;
        this.save(user);
        log.info("insertRequired end");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void insertRequiredNew() {
        log.info("insertRequiredNew start");
        User user = new User();
        user.setName("insertRequiredNew");
        user.setUsername("insertRequiredNew");
        user.setPassword("123456");
        user.setStatus("1");
        user.setId(null);
        this.save(user);
        log.info("insertRequiredNew end");
    }

}
