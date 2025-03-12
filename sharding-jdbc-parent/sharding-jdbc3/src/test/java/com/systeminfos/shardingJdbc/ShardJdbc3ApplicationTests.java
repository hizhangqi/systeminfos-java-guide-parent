package com.systeminfos.shardingJdbc;

import com.systeminfos.shardingJdbc.mapper.UserInfoMapper;
import com.systeminfos.shardingJdbc.model.UserInfo;
import io.shardingsphere.api.HintManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardJdbc3Application.class)
@Slf4j
public class ShardJdbc3ApplicationTests {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Test
    public void test_select() {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(100L);
        System.out.println(userInfo);

        userInfo = userInfoMapper.selectByPrimaryKey(100L);
        System.out.println(userInfo);

        userInfo = userInfoMapper.selectByPrimaryKey(100L);
        System.out.println(userInfo);
    }

    @Test
    public void test_delete() {
        userInfoMapper.deleteByPrimaryKey(1L);
    }

    @Test
    public void test_write_read() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(100L);
        userInfo.setAccount("Account");
        userInfo.setPassword("pass");
        userInfo.setUserName("name");
        int insert = userInfoMapper.insertSelective(userInfo);
        log.info("插入主库结果:" + insert);

        UserInfo search = userInfoMapper.selectByPrimaryKey(100L);
        log.info("查询从库结果:" + search);

        HintManager.getInstance().setMasterRouteOnly();
        search = userInfoMapper.selectByPrimaryKey(100L);
        log.info("查询主库结果:" + search);
    }

    @Test
    public void test_insert() {
        for (int i = 0; i < 100; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId((long) i);
            userInfo.setAccount("Account" + i);
            userInfo.setPassword("pass" + i);
            userInfo.setUserName("name" + i);

            userInfoMapper.insert(userInfo);
        }
    }

}
