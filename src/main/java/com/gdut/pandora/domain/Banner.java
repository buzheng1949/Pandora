package com.gdut.pandora.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by buzheng on 18/4/1.
 * 导航图
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    private Integer id;

    private String image;

    private String link;

    private String category;

    private Long createTime;

    private Long updateTime;

}