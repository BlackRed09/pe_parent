package com.blackred.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blackred.entity.Checkgroup;
import com.blackred.entity.CheckgroupCheckitem;
import com.blackred.mapper.CheckgroupCheckitemMapper;
import com.blackred.mapper.CheckgroupMapper;
import com.blackred.mapper.SetmealCheckgroupMapper;
import com.blackred.service.CheckgroupService;
import com.blackred.utils.MessageConstant;
import com.blackred.utils.PageResult;
import com.blackred.utils.QueryPageBean;
import com.blackred.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
@Service(interfaceClass = CheckgroupService.class)
public class CheckgroupServiceImpl extends ServiceImpl<CheckgroupMapper, Checkgroup> implements CheckgroupService {

    @Resource
    private CheckgroupMapper checkgroupMapper;

    @Resource
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;

    @Resource
    private SetmealCheckgroupMapper setmealCheckgroupMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public PageResult findByPage(QueryPageBean qpb) {
        Page page = new Page<>(qpb.getCurrentPage(),qpb.getPageSize());
        QueryWrapper<Checkgroup> qw = new QueryWrapper<>();
        if (qpb.getQueryString()!=null && !"".equals(qpb.getQueryString())){
            qw.like("code",qpb.getQueryString()).or().like("name",qpb.getQueryString()).or().like("helpCode",qpb.getQueryString());
        }
        Page page1 = checkgroupMapper.selectPage(page, qw);
        return new PageResult(page1.getTotal(),page1.getRecords());
    }

    @Override
    public ResultVo addCheckgroup(Checkgroup checkgroup, Integer[] ids) {
        int insert = checkgroupMapper.insert(checkgroup);

        if (insert>0){
            for (Integer id : ids) {
                CheckgroupCheckitem checkgroupCheckitem = new CheckgroupCheckitem();
                checkgroupCheckitem.setCheckitemId(id);
                checkgroupCheckitem.setCheckgroupId(checkgroup.getId());
                checkgroupCheckitemMapper.insert(checkgroupCheckitem);
                HashOperations opsForHash = redisTemplate.opsForHash();
                opsForHash.delete("checkgroup", "checkgroups");
                return new ResultVo(true, MessageConstant.ADD_CHECKGROUP_SUCCESS,null);
            }
        }
        return new ResultVo(false,MessageConstant.ADD_CHECKGROUP_FAIL,null);

    }

    @Override
    public ResultVo delCheckgroup(Integer id) {
        QueryWrapper qw = new QueryWrapper<>();
        qw.eq("checkgroup_id",id);
        checkgroupCheckitemMapper.delete(qw);
        setmealCheckgroupMapper.delete(qw);
        int i = checkgroupMapper.deleteById(id);
        if (i>0){
            HashOperations opsForHash = redisTemplate.opsForHash();
            opsForHash.delete("checkgroup", "checkgroups");
            return new ResultVo(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS,null);
        }
        return new ResultVo(false,MessageConstant.DELETE_CHECKGROUP_FAIL,null);
    }

    @Override
    public ResultVo updateCheckgroup(Checkgroup checkgroup, Integer[] ids) {

        QueryWrapper qw = new QueryWrapper();
        qw.eq("checkgroup_id",checkgroup.getId());
        checkgroupCheckitemMapper.delete(qw);
        for (Integer id : ids) {
            CheckgroupCheckitem checkgroupCheckitem = new CheckgroupCheckitem();
            checkgroupCheckitem.setCheckitemId(id);
            checkgroupCheckitem.setCheckgroupId(checkgroup.getId());
            checkgroupCheckitemMapper.insert(checkgroupCheckitem);
        }
        int update = checkgroupMapper.updateById(checkgroup);
        if (update>0){
            HashOperations opsForHash = redisTemplate.opsForHash();
            opsForHash.delete("checkgroup", "checkgroups");
            return new ResultVo(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS,null);
        }

        return new ResultVo(true,MessageConstant.EDIT_CHECKGROUP_FAIL,null);
    }

    @Override
    public ResultVo findAll() {
        HashOperations<String,Object,List<Checkgroup>> opsForHash = redisTemplate.opsForHash();
        List<Checkgroup> checkgroups = opsForHash.get("checkgroup", "checkgroups");
        if (checkgroups==null||checkgroups.size()<=0){
            checkgroups=checkgroupMapper.selectList(null);
            opsForHash.put("checkgroup", "checkgroups",checkgroups);
        }
        return new ResultVo(true,"回显数据成功",checkgroups);
    }

}
