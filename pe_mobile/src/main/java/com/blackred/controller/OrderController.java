package com.blackred.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.blackred.entity.Order;
import com.blackred.service.OrderService;
import com.blackred.utils.ResultVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;


    @RequestMapping("/submitOrder")
    public ResultVo submitOrder(@RequestBody Map map){
        return orderService.submitOrder(map);
    }

    @RequestMapping("/findOrderById")
    public ResultVo findOrderById(Integer id){
        return orderService.findOrderById(id);
    }
}

