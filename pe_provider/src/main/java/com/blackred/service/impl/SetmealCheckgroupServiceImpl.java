package com.blackred.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blackred.entity.SetmealCheckgroup;
import com.blackred.mapper.SetmealCheckgroupMapper;
import com.blackred.service.SetmealCheckgroupService;
import com.blackred.utils.MessageConstant;
import com.blackred.utils.ResultVo;
import org.springframework.data.redis.core.RedisTemplate;
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
@Service(interfaceClass = SetmealCheckgroupService.class)
public class SetmealCheckgroupServiceImpl extends ServiceImpl<SetmealCheckgroupMapper, SetmealCheckgroup> implements SetmealCheckgroupService {

    @Resource
    private SetmealCheckgroupMapper setmealCheckgroupMapper;


    @Override
    public ResultVo getCheckgroupId(Integer id) {
        List<Integer> list = new ArrayList<>();
        QueryWrapper qw = new QueryWrapper();
        qw.eq("setmeal_id",id);
        List<SetmealCheckgroup> list1 = setmealCheckgroupMapper.selectList(qw);
        for (SetmealCheckgroup checkgroup : list1) {
            list.add(checkgroup.getCheckgroupId());
        }
        if (list.size()>0){
            return new ResultVo(true, "获取检查组选项成功",list);
        }
        return new ResultVo(false,"获取检查组选项失败",null);
    }
}
