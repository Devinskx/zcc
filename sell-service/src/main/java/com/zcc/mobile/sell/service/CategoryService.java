package com.zcc.mobile.sell.service;

import com.zcc.mobile.sell.common.constant.SellConstant;
import com.zcc.mobile.sell.common.exceptions.SellException;
import com.zcc.mobile.sell.domain.dao.CategoryDao;
import com.zcc.mobile.sell.domain.entity.CategoryEntity;
import com.zcc.mobile.sell.domain.entity.ProductEntity;
import com.zcc.mobile.sell.domain.model.vo.category.CategoryInfoVO;
import com.zcc.mobile.sell.domain.model.vo.category.CategoryType;
import com.zcc.mobile.sell.domain.model.vo.category.ModifyCategoryRequest;
import com.zcc.mobile.sell.domain.model.vo.product.ProductInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Devin sun
 * @date 2022/3/1
 */

@Service
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public List<CategoryInfoVO> getAllCategory() throws SellException {
        List<CategoryEntity> categoryEntities = categoryDao.findAll(SellConstant.ON_USE);
        if (!CollectionUtils.isEmpty(categoryEntities)) {
            return categoryEntities.stream()
                    .map(this::convertEntity2VO)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public void createCategory(ModifyCategoryRequest request) throws Exception {
        if (Objects.isNull(request.getCategoryType())) {
            throw new SellException("Param error!");
        }
        CategoryEntity categoryEntity = convertType2Entity(request.getCategoryType());
        categoryEntity.setStatus(SellConstant.ON_USE);
        int result = categoryDao.createCategory(categoryEntity);
        if (result != 1) {
            throw new SellException("Create category error!");
        }
    }

    public void updateCategory(ModifyCategoryRequest request) throws Exception {
        if (Objects.isNull(request.getCategoryType())) {
            throw new SellException("Param error!");
        }
        CategoryEntity categoryEntity = convertType2Entity(request.getCategoryType());
        int result = categoryDao.updateCategory(categoryEntity);
        if (result != 1) {
            throw new SellException("Update category error!");
        }
    }

    public void deleteCategory(Long id) throws Exception {
        if (Objects.isNull(id)) {
            throw new SellException("Param error!");
        }
        int result = categoryDao.deleteCategory(SellConstant.DROPPED, id);
        if (result != 1) {
            throw new SellException("Update category error!");
        }
    }

    private CategoryEntity convertType2Entity(CategoryType categoryType) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryType.getName());
        categoryEntity.setType(categoryType.getType());
        categoryEntity.setDescription(categoryType.getDescription());
        return categoryEntity;
    }

    private CategoryInfoVO convertEntity2VO(CategoryEntity category) {
        CategoryInfoVO categoryInfoVO = new CategoryInfoVO();
        categoryInfoVO.setCreateTime(category.getCreateTime());
        categoryInfoVO.setDescription(category.getDescription());
        categoryInfoVO.setId(category.getId());
        categoryInfoVO.setName(category.getName());
        categoryInfoVO.setType(category.getType());
        categoryInfoVO.setUpdateTime(category.getUpdateTime());
        return categoryInfoVO;
    }
}
