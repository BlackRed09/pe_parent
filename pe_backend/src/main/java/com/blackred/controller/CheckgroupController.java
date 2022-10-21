package com.blackred.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.blackred.entity.Checkgroup;
import com.blackred.service.CheckgroupService;
import com.blackred.utils.PageResult;
import com.blackred.utils.QueryPageBean;
import com.blackred.utils.ResultVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckgroupController {

    @Reference
    private CheckgroupService checkgroupService;

    @RequestMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean qpb){
        return checkgroupService.findByPage(qpb);
    }

    @RequestMapping("/add")
    public ResultVo addCheckgroup(@RequestBody Checkgroup checkgroup,Integer[] ids){
        return checkgroupService.addCheckgroup(checkgroup,ids);
    }

    @RequestMapping("/delete")
    public ResultVo delCheckgroup(Integer id){
        return checkgroupService.delCheckgroup(id);
    }

    @RequestMapping("/update")
    public ResultVo updateCheckgroup(@RequestBody Checkgroup checkgroup,Integer[] ids){
        return checkgroupService.updateCheckgroup(checkgroup,ids);
    }

    @RequestMapping("/findAll")
    public ResultVo findAll(){
        return checkgroupService.findAll();
    }
}

