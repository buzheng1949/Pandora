package com.gdut.pandora.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    private Integer id;

    private String shopName;

    private Integer fans;

    private String image;

    private Integer star;

    private String content;

}