package com.zcc.mobile.sell.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderWithDetailEntity {

    private Long orderId;
    private String openId;
    private BigDecimal totalAmount;
    private Long detailId;
    private Integer productCount;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private String productImage;

    private Date createTime;
}
