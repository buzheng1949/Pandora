package com.gdut.pandora.domain.result;

import lombok.Data;

/**
 * Created by buzheng on 18/4/6.
 * 专题返回结果类
 */
@Data
public class TopicDTO {
    private Integer id;

    private Long userId;

    private String userName;

    private String userImage;

    private String content;

    private String topicImage;

    private boolean isAnonymous;

    private Long likeNum;

    private Long createTime;

    private Long updateTime;
}
