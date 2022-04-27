package com.zcc.mobile.sell.domain.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductWithCategoryEntity {

    private Long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private Integer category;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private String categoryName;
}
