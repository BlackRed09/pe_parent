package com.blackred.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blackred.entity.CheckgroupCheckitem;
import com.blackred.mapper.CheckgroupCheckitemMapper;
import com.blackred.service.CheckgroupCheckitemService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
@Component
@Service(interfaceClass = CheckgroupCheckitemService.class)
public class CheckgroupCheckitemServiceImpl extends ServiceImpl<CheckgroupCheckitemMapper, CheckgroupCheckitem> implements CheckgroupCheckitemService {

    @Resource
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;

    @Override
    public List<Integer> getCheckitemId(String id) {
        List<Integer> list1 = new ArrayList<>();
        QueryWrapper qw = new QueryWrapper();
        qw.eq("checkgroup_id", id);
        List<CheckgroupCheckitem> list = checkgroupCheckitemMapper.selectList(qw);
        for (CheckgroupCheckitem checkitem : list) {
            list1.add(checkitem.getCheckitemId());
        }
        return list1;
    }
}
