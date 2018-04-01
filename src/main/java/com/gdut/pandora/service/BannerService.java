package com.gdut.pandora.service;

import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.Banner;
import com.gdut.pandora.domain.query.BannerQuery;

import java.util.List;

/**
 * Created by buzheng on 18/4/1.
 * 轮播图查询接口
 */
public interface BannerService {

    /**
     * 查询轮播图接口
     *
     * @param bannerQuery
     * @return
     */
    List<Banner> queryBannerList(BannerQuery bannerQuery);
}
