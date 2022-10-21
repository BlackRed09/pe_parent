package com.blackred.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blackred.entity.Checkitem;
import com.blackred.mapper.CheckgroupCheckitemMapper;
import com.blackred.mapper.CheckitemMapper;
import com.blackred.service.CheckitemService;
import com.blackred.utils.MessageConstant;
import com.blackred.utils.PageResult;
import com.blackred.utils.QueryPageBean;
import com.blackred.utils.ResultVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@Service(interfaceClass = CheckitemService.class)
public class CheckitemServiceImpl extends ServiceImpl<CheckitemMapper, Checkitem> implements CheckitemService {

    @Resource
    private CheckitemMapper checkitemMapper;

    @Resource
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {

        Page page = new Page(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        QueryWrapper<Checkitem> qw = new QueryWrapper<>();
        if (queryPageBean.getQueryString()!=null&& !"".equals(queryPageBean.getQueryString())){
            qw.like("code",queryPageBean.getQueryString()).or().like("name",queryPageBean.getQueryString());
        }
        Page page1 = checkitemMapper.selectPage(page, qw);
        return new PageResult(page1.getTotal(),page1.getRecords());

    }

    @Override
    public ResultVo addCheckitem(Checkitem checkitem) {
        if (checkitemMapper.insert(checkitem)>0){
            HashOperations hashOperations = redisTemplate.opsForHash();
            hashOperations.delete("checkItems", "checkItemList");
            return new ResultVo(true, MessageConstant.ADD_CHECKITEM_SUCCESS,null);
        }
        return new ResultVo(false,MessageConstant.ADD_CHECKITEM_FAIL,null);
    }

    @Override
    public boolean deleteCheckitem(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("checkitem_id",id);
        checkgroupCheckitemMapper.delete(queryWrapper);
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete("checkItems", "checkItemList");

        return checkitemMapper.deleteById(id)>0?true:false;
    }

    @Override
    public boolean updateCheckitem(Checkitem checkitem) {
        int i = checkitemMapper.updateById(checkitem);
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete("checkItems", "checkItemList");
        return i>0?true:false;
    }

    @Override
    public ResultVo findAll() {
            HashOperations<String, Object,List<Checkitem>> opsForHash = redisTemplate.opsForHash();
            List<Checkitem> checkitems = opsForHash.get("checkItems", "checkItemList");
            if (checkitems==null||checkitems.size()==0){
                checkitems = checkitemMapper.selectList(null);
                opsForHash.put("checkItems", "checkItemList",checkitems);
            }
            return new ResultVo(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitems);

    }


}
