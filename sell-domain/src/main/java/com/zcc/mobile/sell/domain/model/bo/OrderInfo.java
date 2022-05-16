package com.zcc.mobile.sell.domain.model.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderInfo {

    private Long orderId;
    private String orderCode;
    private String openId;
    private BigDecimal totalAmount;
    private List<OrderDetailInfo> orderDetails;
    private String createTime;
}
