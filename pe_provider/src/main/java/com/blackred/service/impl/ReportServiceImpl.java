package com.blackred.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blackred.entity.Member;
import com.blackred.entity.Order;
import com.blackred.entity.Setmeal;
import com.blackred.mapper.MemberMapper;
import com.blackred.mapper.OrderMapper;
import com.blackred.mapper.ReportMapper;
import com.blackred.mapper.SetmealMapper;
import com.blackred.service.ReportService;
import com.blackred.utils.DateUtils;
import com.blackred.utils.ResultVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = ReportService.class)
@Component
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private SetmealMapper setmealMapper;

    @Override
    public ResultVo getBusinessReportData() {
        Map<String,Object> reportData = new HashMap<>();
        try {
//        reportDate:null,
//                todayNewMember :0,
//                totalMember :0,
//                thisWeekNewMember :0,
//                thisMonthNewMember :0,
//                todayOrderNumber :0,
//                todayVisitsNumber :0,
//                thisWeekOrderNumber :0,
//                thisWeekVisitsNumber :0,
//                thisMonthOrderNumber :0,
//                thisMonthVisitsNumber :0,
//                hotSetmeal :[
//        {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
//        {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222}
//                    ]

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String now = simpleDateFormat.format(new Date());


            QueryWrapper<Member> qw1 = new QueryWrapper<>();
            qw1.eq("regTime",now);
            Integer todayNewMember = memberMapper.selectCount(qw1);


            Integer totalMember = memberMapper.selectCount(null);


            Date firstDayOfWeek = DateUtils.getFirstDayOfWeek(new Date());
            String startOfWeek = DateUtils.parseDate2String(firstDayOfWeek);
            Date lastDayOfWeek = DateUtils.getLastDayOfWeek(new Date());
            String endOfWeek = DateUtils.parseDate2String(lastDayOfWeek);
            QueryWrapper qw2 = new QueryWrapper();
            qw2.between("regTime",startOfWeek,endOfWeek);
            Integer thisWeekNewMember = memberMapper.selectCount(qw2);


            Date firstDayOfMonth = DateUtils.getFirstDay4ThisMonth();
            String startOfMonth = DateUtils.parseDate2String(firstDayOfMonth);
            QueryWrapper qw3 = new QueryWrapper();
            qw3.between("regTime",startOfMonth,now);
            Integer thisMonthNewMember = memberMapper.selectCount(qw3);


            QueryWrapper qw4 = new QueryWrapper<>();
            qw4.eq("orderDate",now);
            Integer todayOrderNumber = orderMapper.selectCount(qw4);


            QueryWrapper qw5 = new QueryWrapper<>();
            qw5.eq("orderStatus","已就诊");
            Integer todayVisitsNumber = orderMapper.selectCount(qw5);



            QueryWrapper qw6 = new QueryWrapper<>();
            qw6.between("orderDate",startOfWeek,endOfWeek);
            Integer thisWeekOrderNumber = orderMapper.selectCount(qw6);


            QueryWrapper<Order> qw7 = new QueryWrapper<>();
            qw7.between("orderDate",startOfWeek,endOfWeek).eq("orderStatus","已就诊");
            Integer thisWeekVisitsNumber = orderMapper.selectCount(qw7);


            QueryWrapper qw8 = new QueryWrapper();
            qw8.likeRight("orderDate",now.substring(0,now.lastIndexOf("-")));
            Integer thisMonthOrderNumber = orderMapper.selectCount(qw8);



            qw8.eq("orderStatus","已就诊");
            Integer thisMonthVisitsNumber = orderMapper.selectCount(qw8);


            List<Integer> setmealIds = orderMapper.findHotSetmeal();
            List<Map<String,Object>> hotSetmeal=new ArrayList<>();
            for (Integer setmealId : setmealIds) {
                Map<String,Object> map = new HashMap<>();
                QueryWrapper qw9 = new QueryWrapper<>();
                qw9.eq("setmeal_id",setmealId);
                Integer row = orderMapper.selectCount(qw9);
                Setmeal setmeal = setmealMapper.selectById(setmealId);
                Integer totalNum = orderMapper.selectCount(null);

                map.put("name",setmeal.getName());
                map.put("setmeal_count",row);
                map.put("proportion",row*1.0/totalNum);
                hotSetmeal.add(map);
            }

            reportData.put("reportDate",now);
            reportData.put("todayNewMember",todayNewMember);
            reportData.put("totalMember",totalMember);
            reportData.put("thisWeekNewMember",thisWeekNewMember);
            reportData.put("thisMonthNewMember",thisMonthNewMember);
            reportData.put("todayOrderNumber",todayOrderNumber);
            reportData.put("todayVisitsNumber",todayVisitsNumber);
            reportData.put("thisWeekOrderNumber",thisWeekOrderNumber);
            reportData.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
            reportData.put("thisMonthOrderNumber",thisMonthOrderNumber);
            reportData.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
            reportData.put("hotSetmeal",hotSetmeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultVo(true,"获取数据成功",reportData);
    }
}
