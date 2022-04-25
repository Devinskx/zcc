package com.zcc.mobile.sell.service;

import com.zcc.mobile.sell.common.constant.SellConstant;
import com.zcc.mobile.sell.common.exceptions.SellException;
import com.zcc.mobile.sell.common.utils.DateUtils;
import com.zcc.mobile.sell.domain.dao.ProductDao;
import com.zcc.mobile.sell.domain.entity.ProductEntity;
import com.zcc.mobile.sell.domain.model.vo.product.ModifyProductRequest;
import com.zcc.mobile.sell.domain.model.vo.product.ProductInfoVO;
import com.zcc.mobile.sell.domain.model.vo.product.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Devin sun
 * @date 2022/2/27
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public List<ProductInfoVO> getProductList(String condition) throws SellException {
        if (StringUtils.hasText(condition)) {
            condition = "%" + condition + "%";
        }
        List<ProductEntity> productEntities = productDao.findAll(condition);
        if (!CollectionUtils.isEmpty(productEntities)) {
            return productEntities.stream()
                    .filter(Objects::nonNull)
                    .map(this::convertEntity2VO)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public void createProduct(ModifyProductRequest request) throws Exception {
        ProductType productType = request.getProduct();
        if (productType == null) {
            throw new SellException("Param Error!");
        }
        ProductEntity product = convertType2Entity(productType);
        product.setStatus(SellConstant.ON_USE);
        int result = productDao.insert(product);
        if (result != 1) {
            throw new SellException("insert product info error");
        }
    }

    public void updateProduct(ModifyProductRequest request) throws Exception {
        ProductType productType = request.getProduct();
        if (productType == null) {
            throw new SellException("Param Error!");
        }
        ProductEntity product = convertType2Entity(productType);
        int result = productDao.update(product);
        if (result != 1) {
            throw new SellException("update product info error");
        }
    }

    private ProductEntity convertType2Entity(ProductType productType) {
        ProductEntity product = new ProductEntity();
        if (productType.getId() != -1) {
            product.setId(productType.getId());
        }
        product.setCode(productType.getCode());
        product.setName(productType.getName());
        product.setPrice(productType.getPrice());
        product.setDescription(productType.getDescription());
        product.setStatus(productType.getStatus());
        product.setStock(productType.getStock());
        product.setCategory(productType.getCategory());
        product.setImage(productType.getImg());
        return product;
    }

    private ProductInfoVO convertEntity2VO(ProductEntity product) {
        ProductInfoVO productInfo = new ProductInfoVO();
        productInfo.setId(product.getId());
        productInfo.setCode(product.getCode());
        productInfo.setDescription(product.getDescription());
        productInfo.setCategory(product.getCategory());
        productInfo.setPrice(product.getPrice());
        productInfo.setImg(product.getImage());
        productInfo.setName(product.getName());
        productInfo.setStatus(product.getStatus());
        productInfo.setStock(product.getStock());
        productInfo.setCreateTime(DateUtils.dateToString(product.getCreateTime()));
        productInfo.setUpdateTime(DateUtils.dateToString(product.getUpdateTime()));
        return productInfo;
    }
}
