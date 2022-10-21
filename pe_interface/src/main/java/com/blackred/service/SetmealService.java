package com.blackred.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blackred.entity.Setmeal;
import com.blackred.utils.PageResult;
import com.blackred.utils.QueryPageBean;
import com.blackred.utils.ResultVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
public interface SetmealService extends IService<Setmeal> {

    PageResult findByPage(QueryPageBean qpb);

    ResultVo addSetmeal(Setmeal setmeal, Integer[] ids, String imgName);

    ResultVo delSetmeal(Integer id);

    ResultVo updateSetmeal(Setmeal setmeal, Integer[] ids, String imgName);

    ResultVo getAllSetmeal();

    ResultVo findInfoById(Integer id);

    ResultVo getSetmealReport();
}
