package com.gdut.pandora.mapper;

import com.gdut.pandora.domain.Topic;
import com.gdut.pandora.domain.query.TopicQuery;

import java.util.List;

public interface TopicMapper {

    int insert(TopicQuery topicQuery);

    List<Topic> select(TopicQuery topicQuery);

}