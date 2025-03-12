package com.systeminfos.shardingJdbc5.service;

import com.systeminfos.shardingJdbc5.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class UserInfoService {


    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void createUserInfo(int userId, String status) {
        // 设置事务类型为 XA
        TransactionTypeHolder.set(TransactionType.XA);

        // 插入订单到 ds0
        jdbcTemplate.update("INSERT INTO user_info_0 (user_id, user_name, account,password) VALUES (?, ?, ?,?)", userId, "userName" + userId, "account" + userId, "password" + userId);

        userId++;
        // 插入订单到 ds1
        jdbcTemplate.update("INSERT INTO user_info_0 (user_id, user_name, account,password) VALUES (?, ?, ?,?)", userId, "userName" + userId, "account" + userId, "password" + userId);

        userId++;
        jdbcTemplate.update("INSERT INTO user_info_0 (user_id, user_name, account,password) VALUES (?, ?, ?,?)", userId, "userName" + userId, "account" + userId, "password" + userId);

        // 故意抛出异常，测试事务回滚
        if (true) {
            throw new RuntimeException("故意抛出异常，测试事务回滚");
        }
    }

}
