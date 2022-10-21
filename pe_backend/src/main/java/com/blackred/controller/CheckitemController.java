package com.blackred.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.blackred.entity.Checkitem;
import com.blackred.service.CheckitemService;
import com.blackred.utils.MessageConstant;
import com.blackred.utils.PageResult;
import com.blackred.utils.QueryPageBean;
import com.blackred.utils.ResultVo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/checkitem")
public class CheckitemController {

    @Reference
    private CheckitemService checkitemService;

    @RequestMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean qpb){
        return checkitemService.findByPage(qpb);
    }


    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public ResultVo addCheckitem(@RequestBody Checkitem checkitem){
        return checkitemService.addCheckitem(checkitem);
    }

    @RequestMapping("/delete")
    @Secured("ROLE_ADMIN")
    public ResultVo deleteCheckitem(Integer id){
        boolean i = checkitemService.deleteCheckitem(id);
        if (i){
            return new ResultVo(true, MessageConstant.DELETE_CHECKITEM_SUCCESS,null);
        }
        return new ResultVo(false,MessageConstant.DELETE_CHECKITEM_FAIL,null);
    }

    @RequestMapping("/update")
    public ResultVo updateCheckitem(@RequestBody Checkitem checkitem){
        boolean i = checkitemService.updateCheckitem(checkitem);
        if (i){
            return new ResultVo(true,MessageConstant.EDIT_CHECKITEM_SUCCESS,null);
        }
        return new ResultVo(false,MessageConstant.EDIT_CHECKITEM_FAIL,null);
    }

    @RequestMapping("/findAll")
    public ResultVo findAll(){
        return checkitemService.findAll();
    }
}

