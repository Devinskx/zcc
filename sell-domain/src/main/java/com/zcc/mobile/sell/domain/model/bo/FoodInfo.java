package com.zcc.mobile.sell.domain.model.bo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodInfo {

    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal oldPrice;
    private String description;
    private Integer sellCount;
    private Integer rating;
    private String icon;
    private String image;
}
