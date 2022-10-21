package com.blackred.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blackred.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    List<Integer> findHotSetmeal();
}
