package com.zcc.mobile.sell.domain.model.vo.order;

import com.zcc.mobile.sell.domain.model.bo.FoodInfo;
import com.zcc.mobile.sell.domain.model.vo.AbstractRequest;
import com.zcc.mobile.sell.domain.model.vo.product.ProductType;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest extends AbstractRequest {

    private List<ProductType> products;
}
