package com.zcc.mobile.sell.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Devin sun
 * @date 2022/2/27
 */
@Data
public class OrderEntity {

    private Long id;
    private String orderId;
    private String buyer;
    private BigDecimal amount;
    private Integer orderStatus;
    private Integer status;

    private Date createTime;

    private Date updateTime;

}
