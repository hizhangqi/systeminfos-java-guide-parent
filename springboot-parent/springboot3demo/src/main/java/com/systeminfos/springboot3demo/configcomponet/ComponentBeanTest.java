package com.systeminfos.springboot3demo.configcomponet;

import org.springframework.context.annotation.Bean;

/**
 * 当注解为 @Component
 * 会执行两次 生成RoleService对象
 */
//@Component
public class ComponentBeanTest {

    @Bean
    public UserService userService() {
        return new UserService(roleService());
    }

    @Bean
    public RoleService roleService() {
        return new RoleService();
    }

}
