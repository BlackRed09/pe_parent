package com.blackred.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blackred.entity.*;
import com.blackred.mapper.*;
import com.blackred.service.SetmealService;
import com.blackred.utils.MessageConstant;
import com.blackred.utils.PageResult;
import com.blackred.utils.QueryPageBean;
import com.blackred.utils.ResultVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
@Component
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Resource
    private SetmealMapper setmealMapper;

    @Resource
    private SetmealCheckgroupMapper setmealCheckgroupMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private CheckgroupMapper checkgroupMapper;

    @Resource
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;

    @Resource
    private CheckitemMapper checkitemMapper;

    @Resource
    private OrderMapper orderMapper;


    @Override
    public PageResult findByPage(QueryPageBean qpb) {
        Page page = new Page(qpb.getCurrentPage(),qpb.getPageSize());
        QueryWrapper<Setmeal> qw = new QueryWrapper<>();
        if (qpb.getQueryString()!=null&&!"".equals(qpb)){
            qw.like("code",qpb.getQueryString()).or().like("helpCode",qpb.getQueryString()).or().like("name",qpb.getQueryString());
        }
        Page page1 = setmealMapper.selectPage(page, qw);
        return new PageResult(page1.getTotal(),page1.getRecords());
    }


    @Override
    public ResultVo addSetmeal(Setmeal setmeal, Integer[] ids, String imgName) {
        setmeal.setImg(imgName.substring(imgName.lastIndexOf("/")+1));
        int insert = setmealMapper.insert(setmeal);
        if (insert>0){
            SetmealCheckgroup setmealCheckgroup = new SetmealCheckgroup();
            setmealCheckgroup.setSetmealId(setmeal.getId());
            for (Integer id : ids) {
                setmealCheckgroup.setCheckgroupId(id);
                setmealCheckgroupMapper.insert(setmealCheckgroup);
            }
            SetOperations opsForSet = redisTemplate.opsForSet();
            opsForSet.add("dbpic",setmeal.getImg());
            return new ResultVo(true, MessageConstant.ADD_SETMEAL_SUCCESS,null);
        }
        return new ResultVo(false, MessageConstant.ADD_SETMEAL_FAIL,null);
    }


    @Override
    public ResultVo updateSetmeal(Setmeal setmeal, Integer[] ids, String imgName) {
        setmeal.setImg(imgName.substring(imgName.lastIndexOf("/")+1));
        int i = setmealMapper.updateById(setmeal);
        if (i>0){
            QueryWrapper qw = new QueryWrapper();
            qw.eq("setmeal_id",setmeal.getId());
            setmealCheckgroupMapper.delete(qw);

            SetmealCheckgroup setmealCheckgroup = new SetmealCheckgroup();
            setmealCheckgroup.setSetmealId(setmeal.getId());
            for (Integer id : ids) {
                setmealCheckgroup.setCheckgroupId(id);
                setmealCheckgroupMapper.insert(setmealCheckgroup);
            }
            SetOperations opsForSet = redisTemplate.opsForSet();
            opsForSet.add("dbpic",setmeal.getImg());
            return new ResultVo(true, "套餐更改成功",null);
        }
        return new ResultVo(false, "套餐更改失败",null);
    }


    @Override
    public ResultVo delSetmeal(Integer id) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("setmeal_id",id);
        setmealCheckgroupMapper.delete(qw);
        int i = setmealMapper.deleteById(id);
        if (i>0){
            return new ResultVo(true,"删除套餐成功",null);
        }
        return new ResultVo(false,"删除套餐失败",null);
    }


    @Override
    public ResultVo getAllSetmeal() {
        List<Setmeal> setmeals = setmealMapper.selectList(null);
        if (setmeals.size()>0){
            return new ResultVo(true,"查找套餐成功",setmeals);
        }
        return new ResultVo(false,"查找套餐失败",null);
    }

    @Override
    public ResultVo findInfoById(Integer id) {
        Setmeal setmeal = setmealMapper.selectById(id);
        List<Checkgroup> checkgroups = new ArrayList<>();
        QueryWrapper<SetmealCheckgroup> qw = new QueryWrapper();
        qw.eq("setmeal_id",id);
        List<SetmealCheckgroup> setmealCheckgroups = setmealCheckgroupMapper.selectList(qw);
        for (SetmealCheckgroup setmealCheckgroup : setmealCheckgroups) {
            Checkgroup checkgroup = checkgroupMapper.selectById(setmealCheckgroup.getCheckgroupId());
            List<Checkitem> checkitems = new ArrayList<>();
            QueryWrapper<CheckgroupCheckitem> qw1 = new QueryWrapper<>();
            qw1.eq("checkgroup_id",checkgroup.getId());
            List<CheckgroupCheckitem> checkgroupCheckitems = checkgroupCheckitemMapper.selectList(qw1);
            for (CheckgroupCheckitem checkgroupCheckitem : checkgroupCheckitems) {
                Checkitem checkitem = checkitemMapper.selectById(checkgroupCheckitem.getCheckitemId());
                checkitems.add(checkitem);
            }
            checkgroup.setCheckItems(checkitems);
            checkgroups.add(checkgroup);
        }
        setmeal.setCheckGroups(checkgroups);
        if (setmeal!=null){
            return new ResultVo(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }
        return new ResultVo(false,MessageConstant.QUERY_SETMEAL_FAIL,null);
    }

    @Override
    public ResultVo getSetmealReport() {
        Map<String,Object> map = new HashMap<>();
        List<String> setmealNames = new ArrayList<>();
        List<Map<String,Object>> setmealCount = new ArrayList<>();


        List<Setmeal> setmeals = setmealMapper.selectList(null);
        for (Setmeal setmeal : setmeals) {
            setmealNames.add(setmeal.getName());
        }
        for (Setmeal setmeal : setmeals) {
            Map<String,Object> map1 = new HashMap<>();
            QueryWrapper qw = new QueryWrapper();
            qw.eq("setmeal_id",setmeal.getId());
            Integer integer = orderMapper.selectCount(qw);
            map1.put("name",setmeal.getName());
            map1.put("value",integer);
            setmealCount.add(map1);
        }
        map.put("setmealNames",setmealNames);
        map.put("setmealCount",setmealCount);
        return new ResultVo(true,"获取数据成功",map);
    }

}
