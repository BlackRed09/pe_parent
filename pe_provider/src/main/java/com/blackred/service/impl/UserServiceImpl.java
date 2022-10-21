package com.blackred.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blackred.entity.*;
import com.blackred.mapper.*;
import com.blackred.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public User findByUsername(String username) {
        QueryWrapper qw  =new QueryWrapper();
        qw.eq("username",username);
        User user = userMapper.selectOne(qw);
        return user;
    }

    @Override
    public List<Role> findUserRole(Integer id) {
        List<Role> roles = new ArrayList<>();

        QueryWrapper<UserRole> qw = new QueryWrapper();
        qw.eq("user_id",id);
        List<UserRole> userRoles = userRoleMapper.selectList(qw);
        for (UserRole userRole : userRoles) {
            Role role = roleMapper.selectById(userRole.getRoleId());
            roles.add(role);
        }
        return roles;
    }

    @Override
    public List<Permission> findUserPermission(Integer id) {
        List<Permission> permissions = new ArrayList<>();

        QueryWrapper qw  = new QueryWrapper<>();
        qw.eq("role_id",id);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(qw);
        for (RolePermission rolePermission : rolePermissions) {
            Permission permission = permissionMapper.selectById(rolePermission.getPermissionId());
            permissions.add(permission);
        }
        return permissions;
    }
}
