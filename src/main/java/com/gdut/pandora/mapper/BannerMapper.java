package com.gdut.pandora.mapper;

import com.gdut.pandora.domain.Banner;
import com.gdut.pandora.domain.query.BannerQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerMapper {

    List<Banner> fetch(BannerQuery query);

}