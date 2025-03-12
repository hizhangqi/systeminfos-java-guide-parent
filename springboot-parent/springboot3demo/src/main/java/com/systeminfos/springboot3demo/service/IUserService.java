package com.systeminfos.springboot3demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.systeminfos.springboot3demo.entity.User;

import java.util.List;

/**
 * @author zhangqi
 * @version 1.0
 * @desc  服务
 * @date 2024-12-12 11:24:24
 */
public interface IUserService extends IService<User> {

    /**
     *  批量新增重写
     *
     * @param param model集合
     * @return Boolean
     */
    Boolean saveBatch(List<User> param);

    void insertRequired();

    void insertRequiredNew();

}
