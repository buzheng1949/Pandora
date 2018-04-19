package com.gdut.pandora.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Topic {
    private Integer id;

    private Integer userId;

    private String userName;

    private String userImage;

    private String content;

    private String topicImage;

    private Integer height;

    private Integer width;

    private Boolean isAnonymous;

    private Long likeNum;

    private Long createTime;

    private Long updateTime;
}