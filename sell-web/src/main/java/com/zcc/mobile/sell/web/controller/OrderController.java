package com.zcc.mobile.sell.web.controller;

import com.zcc.mobile.sell.common.constant.RestConstant;
import com.zcc.mobile.sell.common.constant.SellConstant;
import com.zcc.mobile.sell.domain.enums.ResponseStatusEnum;
import com.zcc.mobile.sell.domain.model.bo.OrderInfo;
import com.zcc.mobile.sell.domain.model.vo.SellResponse;
import com.zcc.mobile.sell.domain.model.vo.order.CreateOrderRequest;
import com.zcc.mobile.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = RestConstant.REST_PREFIX + RestConstant.ORDER_MODULE,
        produces = "application/json;charset=utf-8")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(RestConstant.LIST)
    public SellResponse getOrders() {
        ResponseStatusEnum responseStatus = ResponseStatusEnum.SUCCESS;
        List<OrderInfo> orderInfos = new ArrayList<>();
        try {
            orderInfos = orderService.getOrderInfos();
            if (!CollectionUtils.isEmpty(orderInfos)) {
                orderInfos.forEach(orderInfo -> {
                    String encodePart = orderInfo.getOpenId()
                            .substring(5, orderInfo.getOpenId().length() - 5);
                    orderInfo.setOpenId(orderInfo.getOpenId()
                            .replace(encodePart, "******"));
                });
            }
        } catch (Exception e) {
            responseStatus = ResponseStatusEnum.FAILURE;
            log.error("get orders error");
        }
        return SellResponse.newBuilder()
                .setData(orderInfos)
                .setCode(responseStatus.getCode())
                .setMessage(responseStatus.getMessage())
                .build();
    }

    @GetMapping(RestConstant.SELLER_PREFIX + RestConstant.LIST)
    public SellResponse getSellerOrders(@Param("seller") String seller) {
        ResponseStatusEnum responseStatus = ResponseStatusEnum.SUCCESS;
        List<OrderInfo> orderInfos = new ArrayList<>();
        try {
            orderInfos = orderService.getSellerOrders(seller);
        } catch (Exception e) {
            responseStatus = ResponseStatusEnum.FAILURE;
            log.error("get seller orders error");
        }
        return SellResponse.newBuilder()
                .setData(orderInfos)
                .setCode(responseStatus.getCode())
                .setMessage(responseStatus.getMessage())
                .build();
    }

    @PostMapping(RestConstant.SELLER_PREFIX + RestConstant.PAY)
    public SellResponse pay(@RequestBody CreateOrderRequest request) {
        String message = "Success!";
        if (Objects.isNull(request)
                || StringUtils.isEmpty(request.getOpenId())
                || CollectionUtils.isEmpty(request.getProducts())) {
            return SellResponse.newBuilder()
                    .setCode(ResponseStatusEnum.SUCCESS.getCode())
                    .setData("Param Error!")
                    .setMessage(message).build();
        }
        try {
            orderService.createOrders(request);
        } catch (Exception e) {
            log.error("create order error for: " + request.getOpenId());
            message = "Create Order Failed!";
        }
        return SellResponse.newBuilder()
                .setCode(ResponseStatusEnum.SUCCESS.getCode())
                .setData(message)
                .setMessage(message).build();
    }
}
