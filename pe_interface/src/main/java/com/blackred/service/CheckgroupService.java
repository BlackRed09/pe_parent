package com.blackred.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blackred.entity.Checkgroup;
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
public interface CheckgroupService extends IService<Checkgroup> {

    PageResult findByPage(QueryPageBean qpb);

    ResultVo addCheckgroup(Checkgroup checkgroup, Integer[] id);

    ResultVo delCheckgroup(Integer id);

    ResultVo updateCheckgroup(Checkgroup checkgroup, Integer[] ids);

    ResultVo findAll();
}
