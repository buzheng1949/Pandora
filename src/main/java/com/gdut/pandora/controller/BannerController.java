package com.gdut.pandora.controller;

import com.gdut.pandora.common.Constant;
import com.gdut.pandora.common.ResponseCode;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.Banner;
import com.gdut.pandora.domain.query.BannerQuery;
import com.gdut.pandora.service.BannerService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by buzheng on 18/4/1.
 * 轮播接口
 */
@RestController
@ResponseBody
@RequestMapping("/banner")
@Slf4j
public class BannerController {


    @Autowired
    private BannerService bannerService;

    @RequestMapping("/query")
    public ServerResponse<List<Banner>> query(BannerQuery bannerQuery) {
        List<Banner> bannerList = Lists.newArrayList();
        BannerQuery query = bannerQuery;
        try {
            if (query == null) {
                query = new BannerQuery();
            }
            //只取最前面的5条
            query.setStart(Constant.QueryParams.START);
            query.setPageSize(Constant.QueryParams.PAGE_SIZE);
            bannerList = bannerService.queryBannerList(bannerQuery);
        } catch (Exception e) {
            log.error("query the bannerList error", e);
            return ServerResponse.createByErrorMessage("查询轮播列表异常");
        }
        return ServerResponse.createBySuccess(ResponseCode.SUCCESS.getDesc(),bannerList);
    }
}
