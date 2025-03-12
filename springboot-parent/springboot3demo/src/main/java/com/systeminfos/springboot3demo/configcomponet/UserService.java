package com.systeminfos.springboot3demo.configcomponet;

public class UserService {

    RoleService roleService;

    public UserService(RoleService roleService) {
        System.out.println("UserService创建：" + roleService.getClass());
        this.roleService = roleService;
    }

}