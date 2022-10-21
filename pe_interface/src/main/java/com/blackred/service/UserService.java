package com.blackred.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blackred.entity.Permission;
import com.blackred.entity.Role;
import com.blackred.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    User findByUsername(String username);

    List<Role> findUserRole(Integer id);

    List<Permission> findUserPermission(Integer id);
}
