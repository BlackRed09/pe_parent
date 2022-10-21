package com.blackred.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.blackred.service.OrdersettingService;
import com.blackred.utils.MessageConstant;
import com.blackred.utils.POIUtils;
import com.blackred.utils.ResultVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {

    @Reference
    private OrdersettingService ordersettingService;

    @RequestMapping("/uploadTempleate")
    public ResultVo uploadTempleate(@RequestParam("excelFile") MultipartFile multipartFile){
        boolean row =false;
        try {
            List<String[]> list = POIUtils.readExcel(multipartFile);
            row = ordersettingService.saveFile(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (row){
            return new ResultVo(true, "预约设置成功",null);
        }else {
            return new ResultVo(false,"预约设置失败",false);
        }
    }

    @RequestMapping("/getLeftobj")
    public ResultVo getLeftobj(String date){
        return ordersettingService.getData(date);
    }


    @RequestMapping("/setOrderSingle")
    public ResultVo setOrderSingle(String date,Integer number){
        return ordersettingService.setOrderSingle(date,number);

    }
}

