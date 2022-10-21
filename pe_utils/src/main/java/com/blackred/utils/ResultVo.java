package com.blackred.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo implements Serializable {
    private boolean flag;//执⾏结果，true为执⾏成功 false为执⾏失败
    private String message;//返回结果信息，主要⽤于⻚⾯提示信息
    private Object data;//返回数据
}
