package com.gdut.pandora.service.impl;

import com.gdut.pandora.domain.Topic;
import com.gdut.pandora.domain.query.TopicQuery;
import com.gdut.pandora.domain.result.TopicDTO;
import com.gdut.pandora.mapper.TopicMapper;
import com.gdut.pandora.service.TopicService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by buzheng on 18/4/6.
 * 专题服务实现类
 */
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public List<TopicDTO> listTopic(TopicQuery topicQuery) {
        List<TopicDTO> resultTopicList = Lists.newArrayList();
        List<Topic> topicList = topicMapper.select(topicQuery);
        if (CollectionUtils.isNotEmpty(topicList)) {
            for (Topic topic : topicList) {
                TopicDTO topicDTO = new TopicDTO();
                BeanUtils.copyProperties(topic, topicDTO);
                //伪随机生成100-200之间的点赞数
                long likeNum = (long) Math.random() * 100 + 200;
                topicDTO.setLikeNum(likeNum);
                resultTopicList.add(topicDTO);
            }
        }
        return resultTopicList;
    }

    @Override
    public boolean publicTopic(TopicQuery topicQuery) {
        boolean success = Boolean.FALSE;
        boolean isValidQuery = isValidPublicQuery(topicQuery);
        if (!isValidQuery) {
            throw new RuntimeException("不能传入非法的秀秀专题");
        }
        int res = topicMapper.insert(topicQuery);
        if (res >= 1) {
            success = Boolean.TRUE;
        }
        return success;
    }

    /**
     * 是否是无效的请求
     *
     * @param topicQuery
     * @return
     */
    private boolean isValidPublicQuery(TopicQuery topicQuery) {
        boolean result = Boolean.FALSE;
        if (topicQuery != null
                && topicQuery.getUserId() == null
                && StringUtils.isNotEmpty(topicQuery.getContent())
                && StringUtils.isNotEmpty(topicQuery.getTopicImage())
                ) {
            result = Boolean.TRUE;
        }
        return result;
    }
}
