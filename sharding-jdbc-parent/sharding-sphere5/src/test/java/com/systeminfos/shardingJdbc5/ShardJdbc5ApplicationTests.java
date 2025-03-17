package com.systeminfos.shardingJdbc5;

import com.systeminfos.shardingJdbc5.mapper.UserInfoMapper;
import com.systeminfos.shardingJdbc5.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardJdbc5Application.class, properties = "spring.config.location=classpath:application.properties")
@Slf4j
public class ShardJdbc5ApplicationTests {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createDB() {
        String dropSql = "DROP TABLE IF EXISTS `user_info`; ";
        String createSql = "CREATE TABLE `user_info` (\n" +
                "  `user_id` bigint(19) NOT NULL,\n" +
                "  `user_name` varchar(45) DEFAULT NULL,\n" +
                "  `account` varchar(45) NOT NULL,\n" +
                "  `password` varchar(45) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`user_id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        jdbcTemplate.execute(dropSql);
        jdbcTemplate.execute(createSql);
    }

    @Test
    public void truncateDB() {
        jdbcTemplate.execute("truncate table user_info");
    }

    @Test
    public void test_select() {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(1L);
        System.out.println(userInfo);

        userInfo = userInfoMapper.selectByPrimaryKey(1L);
        System.out.println(userInfo);

        userInfo = userInfoMapper.selectByPrimaryKey(1L);
        System.out.println(userInfo);
    }

    @Test
    public void seletctList() {
        List<UserInfo> userInfos = userInfoMapper.selectList(null);
        userInfos.forEach(System.out::println);
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

        HintManager.getInstance().setWriteRouteOnly();
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
