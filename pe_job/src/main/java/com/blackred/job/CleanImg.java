package com.blackred.job;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.Set;

@Component
public class CleanImg {

    @Resource
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0/10 * * * * ?")
    private void work(){
        Set difference = redisTemplate.opsForSet().difference("uploadpic", "dbpic");
        if (difference.size()>0||difference!=null){
            for (Object o : difference) {
                File file = new File("D:/upload1/"+o);
                boolean delete = file.delete();
            }
            redisTemplate.opsForSet().remove("uploadpic");
        }
    }
}
