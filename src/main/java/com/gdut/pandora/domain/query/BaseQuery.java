package com.gdut.pandora.domain.query;

import lombok.Data;

/**
 * Created by buzheng on 18/4/1.
 * 基础查询类
 */
@Data
public class BaseQuery {

    private Integer start;

    private Integer pageSize;

    private String orderBy;
}
