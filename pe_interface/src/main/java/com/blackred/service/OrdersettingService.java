package com.blackred.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blackred.entity.Ordersetting;
import com.blackred.utils.ResultVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
public interface OrdersettingService extends IService<Ordersetting> {

    boolean saveFile(List<String[]> list);

    ResultVo getData(String date);

    ResultVo setOrderSingle(String date, Integer number);
}
