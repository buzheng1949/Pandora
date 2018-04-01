package com.gdut.pandora.service.impl;

import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.Banner;
import com.gdut.pandora.domain.query.BannerQuery;
import com.gdut.pandora.mapper.BannerMapper;
import com.gdut.pandora.service.BannerService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by buzheng on 18/4/1.
 * 查询接口
 */
@Service
@Slf4j
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> queryBannerList(BannerQuery bannerQuery) {
        List<Banner> bannerList = Lists.newArrayList();
        try {
            if (bannerQuery == null) {
                return bannerList;
            }
            bannerList = bannerMapper.fetch(bannerQuery);
        } catch (Exception e) {
            log.error("query the banner list error", e);
        }
        return bannerList;
    }
}
