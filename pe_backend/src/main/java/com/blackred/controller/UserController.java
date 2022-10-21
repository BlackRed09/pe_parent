package com.blackred.controller;


import com.blackred.utils.ResultVo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/getUserName")
    public ResultVo getUserName(){
        return new ResultVo(true,"", SecurityContextHolder.getContext().getAuthentication().getName());
    }
}

