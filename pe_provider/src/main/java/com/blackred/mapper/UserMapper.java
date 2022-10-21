package com.blackred.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blackred.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
