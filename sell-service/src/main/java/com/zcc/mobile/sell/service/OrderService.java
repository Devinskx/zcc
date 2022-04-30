package com.zcc.mobile.sell.service;

import com.zcc.mobile.sell.common.constant.SellConstant;
import com.zcc.mobile.sell.common.exceptions.SellException;
import com.zcc.mobile.sell.common.utils.DateUtils;
import com.zcc.mobile.sell.domain.dao.OrderDao;
import com.zcc.mobile.sell.domain.dao.ProductDao;
import com.zcc.mobile.sell.domain.entity.OrderDetailEntity;
import com.zcc.mobile.sell.domain.entity.OrderEntity;
import com.zcc.mobile.sell.domain.entity.OrderWithDetailEntity;
import com.zcc.mobile.sell.domain.entity.ProductEntity;
import com.zcc.mobile.sell.domain.model.bo.OrderDetailInfo;
import com.zcc.mobile.sell.domain.model.bo.OrderInfo;
import com.zcc.mobile.sell.domain.model.bo.ProductInfo;
import com.zcc.mobile.sell.domain.model.vo.order.CreateOrderRequest;
import com.zcc.mobile.sell.domain.model.vo.product.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    private TransactionDefinition transactionDefinition;

    private static final Comparator<OrderInfo> dateComparator =
            (o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime());

    public List<OrderInfo> getOrderInfos() {
        List<OrderInfo> orderInfos = new LinkedList<>();
        List<OrderWithDetailEntity> orderWithDetails =
                orderDao.findOrders(SellConstant.ON_USE);
        if (!CollectionUtils.isEmpty(orderWithDetails)) {
            Map<Long, List<OrderWithDetailEntity>> orderMap = orderWithDetails.stream()
                    .collect(Collectors.groupingBy(OrderWithDetailEntity::getOrderId));
            orderMap.forEach((k, v) -> {
                OrderWithDetailEntity defaultDetailOrder = v.get(0);
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setOrderId(k);
                orderInfo.setOpenId(defaultDetailOrder.getOpenId());
                orderInfo.setOrderDetails(v.stream()
                        .map(detail -> {
                            OrderDetailInfo detailInfo = new OrderDetailInfo();
                            detailInfo.setDetailId(detail.getDetailId());
                            detailInfo.setProductCount(detail.getProductCount());
                            ProductInfo productInfo = new ProductInfo();
                            productInfo.setProductId(detail.getProductId());
                            productInfo.setProductImage(detail.getProductImage());
                            productInfo.setProductName(detail.getProductName());
                            productInfo.setProductPrice(detail.getProductPrice());
                            detailInfo.setProduct(productInfo);
                            return detailInfo;
                        })
                        .collect(Collectors.toList()));
                orderInfo.setTotalAmount(defaultDetailOrder.getTotalAmount());
                orderInfo.setCreateTime(DateUtils.dateToString(defaultDetailOrder.getCreateTime()));
                orderInfos.add(orderInfo);
            });
            orderInfos.sort(dateComparator);
        }
        return orderInfos;
    }

    public List<OrderInfo> getSellerOrders(String seller) {
        List<OrderInfo> orderInfos = getOrderInfos();
        return CollectionUtils.isEmpty(orderInfos) ? orderInfos :
                orderInfos.stream()
                        .filter(order -> order.getOpenId().equalsIgnoreCase(seller))
                        .collect(Collectors.toList());
    }

    public void createOrders(CreateOrderRequest request) throws Exception {
        // 创建主订单
        TransactionStatus status = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            List<ProductType> products = request.getProducts();
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setBuyer(request.getOpenId());
            orderEntity.setOrderId(UUID.randomUUID().toString());
            orderEntity.setStatus(SellConstant.ON_USE);
            orderEntity.setAmount(new BigDecimal(0));
            List<OrderDetailEntity> orderDetails = new LinkedList<>();
            List<ProductEntity> productEntities = new LinkedList<>();
            final BigDecimal[] totalAmount = {new BigDecimal(0)};
            products.forEach(product -> {
                OrderDetailEntity orderDetail = new OrderDetailEntity();
                orderDetail.setProductId(product.getId());
                orderDetail.setProductImage(product.getImg());
                orderDetail.setProductPrice(product.getPrice());
                orderDetail.setProductQuantity(product.getCount());
                orderDetail.setStatus(SellConstant.ON_USE);
                BigDecimal price = product.getPrice().multiply(new BigDecimal(product.getCount()));
                orderDetail.setTotalPrice(price);
                ProductEntity productEntity = productDao.findProductById(product.getId());
                productEntity.setStock(productEntity.getStock() - product.getCount());
                productEntities.add(productEntity);
                totalAmount[0] = totalAmount[0].add(price);
                orderDetails.add(orderDetail);
            });
            orderEntity.setAmount(totalAmount[0]);
            // 保存主订单
            orderDao.saveMainOrder(orderEntity);
            orderDetails.forEach(detail -> detail.setOrderId(orderEntity.getId()));
            // 保存子订单
            orderDao.saveOrderDetails(orderDetails);
            // TODO 改库存
            productEntities.forEach(productDao::update);
//            productDao.updateBatchProducts(productEntities);
            dataSourceTransactionManager.commit(status);
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(status);
            throw new SellException("Operate Failed!");
        }
    }
}
