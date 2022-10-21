package com.blackred.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.blackred.service.SetmealCheckgroupService;
import com.blackred.utils.ResultVo;
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
@RequestMapping("/setmeal-checkgroup")
public class SetmealCheckgroupController {

    @Reference
    private SetmealCheckgroupService setmealCheckgroupService;


    @RequestMapping("/getCheckgroupId")
    public ResultVo getCheckgroupId(Integer id){
        return setmealCheckgroupService.getCheckgroupId(id);
    }
}

