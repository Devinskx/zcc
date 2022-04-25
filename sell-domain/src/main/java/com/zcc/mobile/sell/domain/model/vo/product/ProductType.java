package com.zcc.mobile.sell.domain.model.vo.product;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Devin sun
 * @date 2022/2/27
 */
@Data
public class ProductType {

    private Long id;
    private String name;
    private String code;
    private String img;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Integer category;
    private Integer status;
}
