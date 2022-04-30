package com.zcc.mobile.sell.domain.model.bo;

import lombok.Data;

@Data
public class OrderDetailInfo {

    private Long detailId;
    private ProductInfo product;
    private Integer productCount;

}
