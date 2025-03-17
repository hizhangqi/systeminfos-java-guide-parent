package com.systeminfos.springboot3demo.configcomponet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 当注解为 @Configuration
 * 会执行一次 生成RoleService对象
 */
@Configuration
public class ConfigBeanTest {

    /**
     * 会执行一次 生成RoleService对象
     *
     * @return
     */
    @Bean
    public UserService userService() {
        return new UserService(roleService());
    }

    @Bean
    public RoleService roleService() {
        return new RoleService();
    }

}
