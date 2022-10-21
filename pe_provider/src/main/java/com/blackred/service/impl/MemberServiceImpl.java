package com.blackred.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blackred.entity.Member;
import com.blackred.mapper.MemberMapper;
import com.blackred.service.MemberService;
import com.blackred.utils.ResultVo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
@Component
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource
    private MemberMapper memberMapper;


    @Override
    public ResultVo getMemberReport() {
        Map<String,Object> map = new HashMap();
        List<String> month = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        List<Integer> count = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            Date time = calendar.getTime();
            month.add(new SimpleDateFormat("yyyy-MM").format(time));
        }

        for (String s : month) {
            QueryWrapper qw = new QueryWrapper();
            qw.lt("regTime",s+"-01");
            Integer integer = memberMapper.selectCount(qw);
            count.add(integer);
        }
        map.put("months",month);
        map.put("memberCount",count);
        return new ResultVo(true,"会员数量显示成功",map);
    }
}
