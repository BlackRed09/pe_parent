package com.blackred.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blackred.entity.CheckgroupCheckitem;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
public interface CheckgroupCheckitemService extends IService<CheckgroupCheckitem> {

    List<Integer> getCheckitemId(String id);
}
