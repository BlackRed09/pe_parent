package com.blackred.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blackred.entity.Permission;
import com.blackred.entity.Role;
import com.blackred.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.blackred.entity.User dbuser = userService.findByUsername(username);
        if (dbuser == null){
            throw new UsernameNotFoundException("用户名不存在");
        }


        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles =  userService.findUserRole(dbuser.getId());
        for (Role role : roles) {
            SimpleGrantedAuthority sgs = new SimpleGrantedAuthority(role.getKeyword());
            authorities.add(sgs);

            List<Permission> permissions =  userService.findUserPermission(role.getId());
            for (Permission permission : permissions) {
                SimpleGrantedAuthority sgs1 = new SimpleGrantedAuthority(permission.getKeyword());
                authorities.add(sgs1);
            }
        }


        return new User(username,dbuser.getPassword(),authorities);
    }
}
