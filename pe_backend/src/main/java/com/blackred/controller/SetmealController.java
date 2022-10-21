package com.blackred.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.blackred.entity.Setmeal;
import com.blackred.service.SetmealService;
import com.blackred.utils.PageResult;
import com.blackred.utils.QueryPageBean;
import com.blackred.utils.ResultVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean qpb){
        return setmealService.findByPage(qpb);
    }

    @RequestMapping("/upload")
    public ResultVo uploadImg(@RequestParam("imgFile") MultipartFile multipartFile){
        String originalFilename = multipartFile.getOriginalFilename();
        try {
            originalFilename= UUID.randomUUID()+originalFilename;
            File dir = new File("D:/upload1");
            if (!dir.exists()){
                dir.mkdirs();
            }
            File file=new File(dir,originalFilename);
            multipartFile.transferTo(file);

            SetOperations opsForSet = redisTemplate.opsForSet();
            opsForSet.add("uploadpic",originalFilename);


            return new ResultVo(true,"文件上传成功",originalFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultVo(false,"文件上传失败",null);
    }


    @RequestMapping("/addSetmeal")
    public ResultVo addSetmeal(@RequestBody Setmeal setmeal,Integer[] ids,String imgName){
        return setmealService.addSetmeal(setmeal,ids,imgName);
    }

    @RequestMapping("/delete")
    public ResultVo delSetmeal(Integer id){
        return setmealService.delSetmeal(id);
    }

    @RequestMapping("/updateSetmeal")
    public ResultVo updateSetmeal(@RequestBody Setmeal setmeal,Integer[] ids,String imgName){
        return setmealService.updateSetmeal(setmeal,ids,imgName);
    }
}

