package com.zcc.mobile.sell.domain.dao;

import com.zcc.mobile.sell.domain.entity.OrderDetailEntity;
import com.zcc.mobile.sell.domain.entity.OrderEntity;
import com.zcc.mobile.sell.domain.entity.OrderWithDetailEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDao {

    @Select("select C.orderId,C.orderCode,C.buyer,C.amount,C.detailId,C.product_quantity,D.productId,D.name,D.price,D.image,C.create_time from " +
            "(select A.product_id,B.orderId,B.orderCode,B.buyer,B.amount,A.detailId,A.product_quantity,B.create_time from " +
            "(select id as detailId, order_id, product_id, product_quantity from order_detail where status = #{status}) as A left join " +
            "(select id as orderId,order_id as orderCode, buyer, amount, create_time from order_info where status = #{status}) as B " +
            "on A.order_id = B.orderId) as C left join " +
            "(select id as productId, name, price, image from product_info) as D " +
            "on C.product_id = D.productId")
    @Results({
            @Result(column = "orderId", property = "orderId"),
            @Result(column = "orderCode",property = "orderCode"),
            @Result(column = "buyer", property = "openId"),
            @Result(column = "amount", property = "totalAmount"),
            @Result(column = "detailId", property = "detailId"),
            @Result(column = "product_quantity", property = "productCount"),
            @Result(column = "productId", property = "productId"),
            @Result(column = "name", property = "productName"),
            @Result(column = "price", property = "productPrice"),
            @Result(column = "image", property = "productImage"),
            @Result(column = "create_time", property = "createTime")
    })
    List<OrderWithDetailEntity> findOrders(@Param("status") int status);

    @Insert("insert into order_info (order_id, buyer, amount, status) values " +
            "(#{orderId}, #{buyer}, #{amount}, #{status})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int saveMainOrder(OrderEntity orderEntity);

    @Insert({"<script>",
            "insert into order_detail (order_id, product_id, ",
            "product_quantity, product_price, total_price, product_image, status) values ",
            "<foreach collection='orderDetails' index='index' item='item' separator=','>",
            "(#{item.orderId},#{item.productId},#{item.productQuantity},#{item.productPrice},#{item.totalPrice},#{item.productImage},#{item.status})",
            "</foreach>",
            "</script>"})
    int saveOrderDetails(@Param("orderDetails") List<OrderDetailEntity> orderDetails);
}
