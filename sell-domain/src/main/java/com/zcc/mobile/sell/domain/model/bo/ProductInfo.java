package com.zcc.mobile.sell.domain.model.bo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfo {

    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal productPrice;
}
