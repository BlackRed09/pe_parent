package com.blackred.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blackred.entity.Checkitem;
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

public interface CheckitemService extends IService<Checkitem> {

    PageResult findByPage(QueryPageBean queryPageBean);

    ResultVo addCheckitem(Checkitem checkitem);

    boolean deleteCheckitem(Integer id);

    boolean updateCheckitem(Checkitem checkitem);

    ResultVo findAll();
}
