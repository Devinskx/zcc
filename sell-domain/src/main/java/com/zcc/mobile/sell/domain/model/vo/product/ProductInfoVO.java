package com.zcc.mobile.sell.domain.model.vo.product;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Devin sun
 * @date 2022/2/27
 */
@Data
public class ProductInfoVO {

    private Long id;
    private String code;
    private String name;
    private String description;
    private String img;
    private Integer category;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private String createTime;
    private String updateTime;
}
