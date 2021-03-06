package com.gdut.pandora.domain.query;

import lombok.Data;

/**
 * Created by buzheng on 18/4/6.
 * 秀秀专题查询类
 */
@Data
public class TopicQuery extends BaseQuery{

    private Integer id;

    private Integer userId;

    private String userName;

    private String userImage;

    private String content;

    private String topicImage;

    private String shopName;

    private Integer height;

    private Integer width;

    private Boolean isAnonymous;

    private Integer likeNum;

    private Long createTime;

    private Long updateTime;

}
