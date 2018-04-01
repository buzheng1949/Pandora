package com.gdut.pandora.domain.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by buzheng on 18/4/1.
 * 查询类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerQuery {
    private Integer id;

    private String image;

    private String link;

    private String category;

    private Long createTime;

    private Long updateTime;

    private Integer start;

    private Integer pageSize;
}
