package com.gdut.pandora.domain.result;

import lombok.Data;

/**
 * Created by buzheng on 18/4/6.
 * 专题返回结果类
 */
@Data
public class TopicDTO {
    private Integer id;

    private Integer userId;

    private String userName;

    private String userImage;

    private String content;

    private String topicImage;

    private String shopName;

    private boolean isAnonymous;

    private Integer width;

    private Integer height;

    private Long likeNum;

    private Long createTime;

    private Long updateTime;
}
