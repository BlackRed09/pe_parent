package com.blackred.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blackred.entity.Ordersetting;
import com.blackred.mapper.OrdersettingMapper;
import com.blackred.service.OrdersettingService;
import com.blackred.utils.MessageConstant;
import com.blackred.utils.ResultVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
@Service(interfaceClass = OrdersettingService.class)
public class OrdersettingServiceImpl extends ServiceImpl<OrdersettingMapper, Ordersetting> implements OrdersettingService {

    @Resource
    private OrdersettingMapper ordersettingMapper;


    @Override
    public boolean saveFile(List<String[]> list) {
        int row =0;
        for (String[] strings : list) {
            QueryWrapper qw = new QueryWrapper();
            qw.eq("orderDate",strings[0]);
            Ordersetting one = ordersettingMapper.selectOne(qw);
            if (one==null){
                Ordersetting two = new Ordersetting();
                DateTimeFormatter formatter =DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate localDate = LocalDate.parse(strings[0],formatter);
                two.setOrderdate(localDate);
                two.setNumber(Integer.parseInt(strings[1]));
                row=ordersettingMapper.insert(two);
            }else {
                one.setNumber(Integer.parseInt(strings[1]));
                row=ordersettingMapper.updateById(one);
            }
        }
        if (row>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ResultVo getData(String date) {
        QueryWrapper qw = new QueryWrapper();
        qw.like("orderDate",date);
        List<Ordersetting> ordersettings = ordersettingMapper.selectList(qw);
        List<Map<String,Object>> list = new ArrayList<>();
        for (Ordersetting ordersetting : ordersettings) {
            Map<String,Object> map = new HashMap<>();
            LocalDate localDate = ordersetting.getOrderdate();
            map.put("date",localDate.getDayOfMonth());
            map.put("number",ordersetting.getNumber());
            map.put("reservations",ordersetting.getReservations()==null?0:ordersetting.getReservations());
            list.add(map);
        }
        if (list.size()>0){
            return new ResultVo(true,"获取数据成功",list);
        }
        return new ResultVo(false,"获取数据失败",null);
    }

    @Override
    public ResultVo setOrderSingle(String date, Integer number) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("orderDate",date);
        Ordersetting selectOne = ordersettingMapper.selectOne(qw);
        int row=0;
        if (selectOne==null){
            selectOne = new Ordersetting();
            DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            selectOne.setOrderdate(localDate);
            selectOne.setNumber(number);
            row=ordersettingMapper.insert(selectOne);
        }else {
            selectOne.setNumber(number);
            row=ordersettingMapper.updateById(selectOne);
        }
        if (row>0){
            return new ResultVo(true, "编辑预约人数成功",null);
        }
        return new ResultVo(false, "编辑预约人数失败",null);
    }
}
