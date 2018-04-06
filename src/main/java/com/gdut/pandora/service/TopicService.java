package com.gdut.pandora.service;

import com.gdut.pandora.domain.Topic;
import com.gdut.pandora.domain.query.TopicQuery;
import com.gdut.pandora.domain.result.TopicDTO;

import java.util.List;

/**
 * Created by buzheng on 18/4/6.
 * 秀秀场专题活动接口
 */
public interface TopicService {

    /**
     * 列出当前在线的专题列表
     *
     * @param topicQuery
     * @return
     */
    List<TopicDTO> listTopic(TopicQuery topicQuery);

    /**
     * 发表专题
     *
     * @param topicQuery
     * @return
     */
    boolean publicTopic(TopicQuery topicQuery);
}
