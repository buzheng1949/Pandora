package com.gdut.pandora.domain.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by buzheng on 18/4/1.
 * 商品列表返回结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductActionResult {
    /**
     * 当前请求页数
     */
    private Integer page;
    /**
     * 请求页码
     */
    private Integer pageSize;
    /**
     * 请求结果总数
     */
    private Integer total;
    /**
     * 请求结果返回列表
     */
    private List<ProductDTO> list;
    /**
     * 是否是最后一页
     */
    private boolean end;
    /**
     * 可请求下一页
     */
    private Integer nextPage;
    /**
     * 当前页数
     */
    private Integer currentPage;
}
