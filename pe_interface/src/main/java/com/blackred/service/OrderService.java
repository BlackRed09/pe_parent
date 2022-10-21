package com.blackred.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blackred.entity.Order;
import com.blackred.utils.ResultVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
public interface OrderService extends IService<Order> {

    ResultVo submitOrder(Map map);

    ResultVo findOrderById(Integer id);
}
