package com.blackred.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.blackred.service.SetmealService;
import com.blackred.utils.ResultVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setmealcli")
public class SetmealcliController {

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/getAllSetmeal")
    public ResultVo getAllSetmeal(){
        return setmealService.getAllSetmeal();
    }

    @RequestMapping("/findInfoById")
    public ResultVo findInfoById(Integer id){
        return setmealService.findInfoById(id);
    }
}
