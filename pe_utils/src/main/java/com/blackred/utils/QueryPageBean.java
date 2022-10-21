package com.blackred.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryPageBean implements Serializable {
    private Integer currentPage;//⻚码
    private Integer pageSize;//每⻚记录数
    private String queryString;//查询条件
}
