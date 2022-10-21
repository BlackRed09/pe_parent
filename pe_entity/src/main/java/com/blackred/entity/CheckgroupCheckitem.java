package com.blackred.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xxx
 * @since 2022-09-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_checkgroup_checkitem")
public class CheckgroupCheckitem extends Model {

    private static final long serialVersionUID = 1L;

    private Integer checkgroupId;

    private Integer checkitemId;


}
