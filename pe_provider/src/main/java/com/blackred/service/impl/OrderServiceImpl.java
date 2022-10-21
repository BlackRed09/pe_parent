package com.blackred.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blackred.entity.Member;
import com.blackred.entity.Order;
import com.blackred.entity.Ordersetting;
import com.blackred.entity.Setmeal;
import com.blackred.mapper.MemberMapper;
import com.blackred.mapper.OrderMapper;
import com.blackred.mapper.OrdersettingMapper;
import com.blackred.mapper.SetmealMapper;
import com.blackred.service.OrderService;
import com.blackred.utils.MessageConstant;
import com.blackred.utils.ResultVo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrdersettingMapper ordersettingMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private SetmealMapper setmealMapper;

    @Override
    public ResultVo submitOrder(Map map) {
        QueryWrapper<Ordersetting> qw = new QueryWrapper();
        qw.eq("orderDate",map.get("orderDate"));
        Ordersetting ordersetting = ordersettingMapper.selectOne(qw);
        if (ordersetting==null){
            return new ResultVo(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER,null);
        }
        int i = ordersetting.getReservations() == null ? 0 : ordersetting.getReservations();
        ordersetting.setReservations(i);
        if (ordersetting.getReservations()>=ordersetting.getNumber()){
            return new ResultVo(false,MessageConstant.ORDER_FULL,null);
        }


        QueryWrapper<Member> qw1 = new QueryWrapper();
        qw1.eq("idCard",map.get("idCard"));
        Member member = memberMapper.selectOne(qw1);

        if (member==null){
            member=new Member();
            member.setName((String) map.get("name"));
            member.setPhonenumber((String) map.get("telephone"));
            member.setSex((String) map.get("sex"));
            member.setIdcard((String) map.get("idCard"));
            member.setRegtime(LocalDate.now());
            memberMapper.insert(member);
        }else {
            QueryWrapper<Order> qw2 = new QueryWrapper<>();
            qw2.eq("member_id",member.getId()).eq("setmeal_id",map.get("setmealId")).eq("orderDate",map.get("orderDate"));
            Order order = orderMapper.selectOne(qw2);
            if (order!=null){
                return new ResultVo(false,MessageConstant.HAS_ORDERED,null);
            }
        }

        Order order = new Order();
        order.setMemberId(member.getId());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse((CharSequence) map.get("orderDate"),dateTimeFormatter);
        order.setOrderdate(localDate);
        order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
        order.setOrdertype("微信预约");
        order.setOrderstatus("未就诊");
        int insert = orderMapper.insert(order);
        if (insert>0){
            ordersetting.setReservations(ordersetting.getReservations()+1);
            ordersettingMapper.updateById(ordersetting);
            return new ResultVo(true,MessageConstant.ORDER_SUCCESS,order.getId());
        }

        return new ResultVo(false,"预约失败",null);
    }

    @Override
    public ResultVo findOrderById(Integer id) {

        Map map = new HashMap();
        Order order = orderMapper.selectById(id);
        Setmeal setmeal = setmealMapper.selectById(order.getSetmealId());
        Member member = memberMapper.selectById(order.getMemberId());
        map.put("member",member.getName());
        map.put("setmeal",setmeal.getName());
        map.put("orderDate",order.getOrderdate());
        map.put("orderType",order.getOrdertype());

        return new ResultVo(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
    }
}
