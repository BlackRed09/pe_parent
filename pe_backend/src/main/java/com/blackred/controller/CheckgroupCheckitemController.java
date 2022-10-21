package com.blackred.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.blackred.service.CheckgroupCheckitemService;
import com.blackred.utils.MessageConstant;
import com.blackred.utils.ResultVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/checkgroup-checkitem")
public class CheckgroupCheckitemController {

    @Reference
    private CheckgroupCheckitemService checkgroupCheckitemService;

    @RequestMapping("/getCheckitemId")
    public ResultVo getCheckitemId(String id){
        List<Integer> list= checkgroupCheckitemService.getCheckitemId(id);
        return new ResultVo(true, "",list);
    }
}

